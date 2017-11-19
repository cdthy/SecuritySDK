package com.nstl.securitysdkcore.crypt;

import com.nstl.securitysdkcore.crypt.bean.EncryptData;

import java.io.InputStream;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by plldzy on 17-11-15.
 */

public class CryptAndHttps {
    //初始化AES的key
    private static byte[] getAESKey(){
        return null;
    }
    // AES加密
    private static byte[] encryptDataByAES(String data, byte[] keyByte){
        return null;
    }

    //用AES加密内容，然后非对称公钥加密AES的key
    public static EncryptData aesEocdeBodyAsymmetricEncodeKey(String sourceData, String publicKey, int type) throws Exception {
        byte aesKey[] = getAESKey();
        EncryptData encryptData = new EncryptData();
        encryptData.setEncryContent(encryptDataByAES(sourceData, aesKey));
        if(type == 1){  //type=1,表示RSA加密key
            encryptData.setEncryKey(encryptByRSAPublicKey(aesKey, publicKey));
        }else{
            encryptData.setEncryKey(encryptByECCPublicKey(aesKey, publicKey));
        }
        return encryptData;
    }
    //RSA公钥加密
    public static byte[] encryptByRSAPublicKey(byte[] data, String rsaPublicKey){
        return null;
    }
    //ECC公钥加密
    public static byte[] encryptByECCPublicKey(byte[] data, String eccPublicKey){
        return null;
    }
    //RSA签名校验
    public static boolean verifyByRSA(byte[] data, String rsaPublicKey, String sign){
        return false;
    }
    //ECC签名校验
    public static boolean verifyByECC(byte[] data, String eccPublicKey, String sign){
        return false;
    }
    //生成信息摘要算法，type=1表示sha256,type=2表示sha512
    public static String getHashBySHA(String sourceStr, int type){
        return null;
    }
    /**
     * 根据客户端自定义证书，进行Https安全通信
     * @param urlString             需要通信的url
     * @param ipAndHosts            证书所签发的域名或者IP地址列表,进行hostnameverify的验证
     * @param certInputstream      证书的输入流
     */
    public static HttpsURLConnection getHttpsUrlConnection(String urlString, List<String> ipAndHosts, InputStream certInputstream){
        return null;
    }

}
