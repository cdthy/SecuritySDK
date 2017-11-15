package com.nstl.securitysdkcore;

/**
 * Created by plldzy on 17-11-15.
 */

public class SecuritySDKInit {
    static {
        System.loadLibrary("native-lib");
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
