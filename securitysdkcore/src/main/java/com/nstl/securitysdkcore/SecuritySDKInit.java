package com.nstl.securitysdkcore;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by plldzy on 17-11-15.
 */

/**
 * SDK的配置文件更新和获取指定key的value
 */
public class SecuritySDKInit {
    private String configName = "securitysdk_config.json";
    private Context context = null;
    private static SecuritySDKInit sdkInit = null;
    SharedPreferences preferences = null;

    private SecuritySDKInit(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(configName, Context.MODE_PRIVATE);
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public void syncConfig(String updateUrl, String token){
        //HttpUtil.doPost()
        //根据根目录下的config.json进行key-value格式编写
    }
    //获得配置文件中指定key的值，如webview的参数
    public String getConfigValueByKey(String keyName){
        return preferences.getString(keyName,null);
    }

    public static synchronized SecuritySDKInit getInstance(Context context){
        if (sdkInit == null)
            sdkInit = new SecuritySDKInit(context);
        return sdkInit;
    }

}
