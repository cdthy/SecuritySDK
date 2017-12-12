### SecuritySDK
安全SDK，通过该sdk为Android APP提供一系列安全防护功能，如基本对抗防护、漏洞防护、威胁情报收集等功能。

一、配置文件的更新和获取(配置文件格式，参考根目录下的config.json)：
1）配置文件：securitysdk_config.json(通过preferences方式存储)，是基于json格式的内容，它提供了4类拦截规则：webview的拦截规则、intent uri拦截规则、应用和插件更新的url白名单和应用插件调用的过滤拦截规则
2）通过SecuritySDKInit.syncConfig(url, params)方法进行配置文件的更新，新版本的更新取决于设置的time_out和version，当更新间隔时间到了，就回去服务器请求新的版本号，如果版本号大于本地版本号，就会进行规则更新；
3）获得拦截规则：先通过SecuritySDKInit.getConfigStringValueByKey(key)获得本地存储的配置文件json内容，然后自己通过fastjson讲字符串在转换成SecuritySDKConfig中的相关拦截规则对象。示例代码如下：
String str = SecuritySDKInit.getConfigStringValueByKey(SecuritySDKInit.WEBVIEWCONFIG);
WebviewConfig webviewConfig = JSON.parseObject(str, WebviewConfig.class);

项目参与成员：daizy(daizhongyin@126.com)、张林、唐海洋。
注意:securicysdkcore是安全sdk的核心功能实现，app是测试安全sdk的demo apk