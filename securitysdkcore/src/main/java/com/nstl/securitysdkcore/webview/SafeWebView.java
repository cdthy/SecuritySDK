package com.nstl.securitysdkcore.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nstl.securitysdkcore.webview.bean.UrlControlsBean;
import com.nstl.securitysdkcore.webview.bean.WebViewConfigBean;

import java.util.List;
import java.util.Map;

/**
 * Created by daizhongyin on 2017/4/27.
 */

public class SafeWebView extends WebView {
    private Map<String, String> methodMap = null;                   //禁止JS和本地代码进行交互的方法名和相关参数
    private IMethodInvokeInterface invokeInterface = null;          //业务方根据JS传递进来的参数，进行实际处理的类
    private WebViewConfigBean webViewConfig = null;
    private WebSettings settings = null;
    private WebViewClient client = null;
    public SafeWebView(Context context,Map<String, String> methodMap, IMethodInvokeInterface miInterface) {
        super(context);
        settings = this.getSettings();
        this.methodMap = methodMap;
        this.invokeInterface = miInterface;
        client = new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                //停止对错误的https证书的继续加载
                handler.cancel();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
            }

            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                super.onUnhandledKeyEvent(view, event);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }
        };
        init();
    }

    //客户端调用SafeWebView后，传递相关参数进行初始化
    public void init(){
        //从配置文件中读取webview的配置策略：url黑、白名单，需要阻止JS执行的方法名和方法参数。
        webViewConfig = null;
        if(Build.VERSION.SDK_INT >=16){
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowFileAccessFromFileURLs(false);
        }
        if(Build.VERSION.SDK_INT <= 16){
            removeJavascriptInterface("searchBoxJavaBridge_");
            removeJavascriptInterface("accessibility");
            removeJavascriptInterface("accessibilityTraversal");
        }
        this.setWebViewClient(client);
    }
    //只加载可信域的https
    private boolean verifyUrl(String url){
        //对url的host做校验,以及是否是Https做判断
        List<UrlControlsBean> urlControlsBeanList = webViewConfig.getUrlList();

        return true;
    }
    @JavascriptInterface
    private String handleMsgAndDispatch(String methodName, String argJson, String callBackFunc){

        String resultJson = null;
        if(verifyMethodNameOrArg(methodName, argJson)){         //合法
            //确定JS和Java方法回调方式(callBackFunc)，和dispatch方法执行后的返回值
            resultJson = invokeInterface.dispatch(methodName, argJson);
            if(callBackFunc != null){
                //JS希望本地代码通过回调的方式，传递执行结果给JS中的方法
                this.loadUrl("javascript:"+callBackFunc+"("+ resultJson+")");
                //已通过回调的方式传递结果，本方法不在返回执行结果。
                resultJson = null;
            }
        }
        return resultJson;
    }
    //校验JS此次调用的方法名和参数，根据WebViewConfigBean相关变量进行判断
    private boolean verifyMethodNameOrArg(String methodName, String argJson){
        return false;
    }
    @Override
    public void loadUrl(String url) {
        if(verifyUrl(url)){
            settings.setJavaScriptEnabled(true);
            this.addJavascriptInterface(this, "safeobj");
        }else{
			settings.setJavaScriptEnabled(false);
		}
        if(url.trim().startsWith("file")){
            //开启File协议，则禁止JS代码执行
            settings.setJavaScriptEnabled(false);
        }
        if(url.startsWith("javascript:")){
            settings.setJavaScriptEnabled(true);
        }
        super.loadUrl(url);
    }
}
