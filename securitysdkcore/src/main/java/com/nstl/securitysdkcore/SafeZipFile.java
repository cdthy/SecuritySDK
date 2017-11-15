package com.nstl.securitysdkcore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
/**
 * Created by plldzy on 17-11-15.
 */


/**
 * 为了保证ZIPFile读取APK文件的安全性，确保只有一个Dex，此外还需要确保APK中的签名和其自生代码的信息一致
 */
public class SafeZipFile extends ZipFile {
    private String apkFilePath = null;
    private boolean zipFileIsIllegal = false;

    public SafeZipFile(File file) throws ZipException, IOException {
        super(file);
        apkFilePath = file.getAbsolutePath();
    }

    /**
     * true表示apk或者签名zip为非法的,false表示zip或者apk合法
     * @param ct            Context上下文
     * @param md5Sig           要校验的签名证书的MD5值(可以通过keytool工具来查看)
     * @return
     */
    public boolean checkSig(Context ct,String md5Sig){
        if(dexCheck() || apkSignCheck(ct, md5Sig)){
            zipFileIsIllegal = true;
        }else{
            zipFileIsIllegal = false;
        }
        return zipFileIsIllegal;
    }

    @Override
    public ZipEntry getEntry(String name) {
        if (zipFileIsIllegal){
            return null;
        }
        return super.getEntry(name);
    }

    @Override
    public InputStream getInputStream(ZipEntry entry) throws IOException {
        if (zipFileIsIllegal){
            return null;
        }
        return super.getInputStream(entry);
    }

    @Override
    public Enumeration<? extends ZipEntry> entries() {
        if (zipFileIsIllegal){
            return null;
        }
        return super.entries();
    }
    private boolean dexCheck(){
        //检查APK中的所有dex文件，确保不存在相同的姓名，注意多个dex文件的情况-classes.dex,classes2.dex
        return true;
    }
    private boolean apkSignCheck(Context ct, String sig){
        //2.读取APK中的META-INF目录下的签名文件，验证一致性
        boolean flag = true;
        if (TextUtils.isEmpty(apkFilePath))
            return true;
        PackageManager pm = ct.getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_SIGNATURES);
        if(pi != null){
            Signature[] signatures = pi.signatures;
            if(signatures != null && signatures.length > 0){
                String signature =  encryptionMD5(signatures[0].toByteArray());
                if(signature.equals(sig))
                    flag = false;
            }
        }
        return flag;
    }
    private  String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }
}