package com.nstl.securitysdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nstl.securitysdkcore.HelpUtil;
import com.nstl.securitysdkcore.NativeCoreUtil;
import com.nstl.securitysdkcore.reinforce.DetectRootUtil;
import com.nstl.securitysdkcore.reinforce.IVerifyListener;
import com.nstl.securitysdkcore.reinforce.bean.InstallPackageInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MaintActivity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.root_id);
        //tv.setText("hello“");
        /*HelpUtil helpUtil = new HelpUtil();
        List<InstallPackageInfo> installPackageInfoList = helpUtil.getInstallPackageAndSig(this);
        StringBuilder builder = new StringBuilder();
        for(InstallPackageInfo pkg : installPackageInfoList){
            builder.append(pkg.getPkgName());
        }*/
        DetectRootUtil detectRootUtil = DetectRootUtil.getInstance(this);
        tv.setText("设备是否：" + detectRootUtil.isRoot());
        final  TextView tv1=(TextView) findViewById(R.id.checkSign_id);
        NativeCoreUtil nativeCoreUtil=new NativeCoreUtil();
        nativeCoreUtil.rePackage(MainActivity.this, new IVerifyListener() {
            @Override
            public void onVerifySuccess() {
                tv1.setText("签名校验通过");
                Log.d(TAG, "onVerifySuccess: 签名没问题");
            }

            @Override
            public void onVerifyFail() {
                tv1.setText("签名校验不通过");
                Log.d(TAG, "onVerifyFail: 签名出问题了");
            }
        });
        final  TextView tv2=(TextView) findViewById(R.id.InjectDetected_id);
        int flag=nativeCoreUtil.detectInject(MainActivity.this);
        if(flag==-1){
            tv2.setText("未发现设备被注入");
        };
        if (flag==1){
            tv2.setText("设备被注入");
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
}
