package com.nstl.securitysdkcore;

import java.util.Map;

/**
 * Created by plldzy on 17-11-15.
 * 进行Http请求:get和post
 * 推荐使用https，如果是自定义证书，可以调用CryptAndHttps中的api进行通信
 */

public class HttpUtil {
    public static String doGet(String url){
        return null;
    }
    //post会提交相关参数，如cookie等账号信息，所以需要params参数
    public static String doPost(String url, Map<String, String> params){
        return null;
    }
}
