package com.nstl.securitysdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nstl.securitysdkcore.HelpUtil;
import com.nstl.securitysdkcore.reinforce.DetectRootUtil;
import com.nstl.securitysdkcore.reinforce.bean.InstallPackageInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText("hello“");
        /*HelpUtil helpUtil = new HelpUtil();
        List<InstallPackageInfo> installPackageInfoList = helpUtil.getInstallPackageAndSig(this);
        StringBuilder builder = new StringBuilder();
        for(InstallPackageInfo pkg : installPackageInfoList){
            builder.append(pkg.getPkgName());
        }*/
        DetectRootUtil detectRootUtil = DetectRootUtil.getInstance(this);
        tv.setText("设备是否：" + detectRootUtil.isRoot());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
}
