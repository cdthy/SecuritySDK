package com.nstl.securitysdkcore;

/**
 * Created by plldzy on 17-11-15.
 */
/*
核心能力的本地代码实现
 */
public class NativeCoreUtil {
    static {
        System.loadLibrary("native-lib");
    }
    //public native String stringFromJNI();
    public native int debugPresent();		// 调试
    public native int runInEmulator(Object mContext);	// 模拟器
    public native void rePackage(IVerifyListener verifyListener);		// 重打包
    public native int detectInject();       //注入检测

}
