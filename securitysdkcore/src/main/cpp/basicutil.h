//
// Created by plldzy on 17-11-15.
//基础工具类:1)字符串jstring处理；2)生成错误信息；3)byte数组和char数组转换;4）log信息输出
//
#include <jni.h>
#include <string>
#include <android/log.h>

extern "C"
#ifndef SECURITYSDK_BASICUTIL_H
#define SECURITYSDK_BASICUTIL_H

#endif //SECURITYSDK_BASICUTIL_H

#define LOG_TAG    "security-sdk-core" // 这个是自定义的LOG的标识
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG, __VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG, __VA_ARGS__)
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG, __VA_ARGS__)
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG_TAG, __VA_ARGS__)
/**
 * jni中string转化成char[]
 * 由于jvm和c++对中文的编码不一样，暂不支持
 * @param env
 * @param jstr
 * @return 失败返回NULL
 */
char* jstringToChar(JNIEnv* env, jstring jstr) {
    const char *c_str = NULL;
    jboolean isCopy = NULL;    // 返回JNI_TRUE表示原字符串的拷贝，返回JNI_FALSE表示返回原字符串的指针
    c_str = env->GetStringUTFChars(jstr, &isCopy);
    printf("isCopy:%d\n",isCopy);
    if(c_str == NULL)
        return NULL;
    int len = strlen(c_str);

    char* rtn = new char[len + 1];
    memset(rtn,0,len + 1);
    memcpy(rtn, c_str, len);
    rtn[len] = 0;
    env->ReleaseStringUTFChars(jstr, c_str);
    return rtn;
}
//把char数组转化成jstring对象，失败返回NULL
jstring charsTojstring(JNIEnv* env, char* str){
    jstring rtn = NULL;
    int slen = strlen(str);
    if (slen != 0)
        rtn = env->NewStringUTF(str);
    return rtn;
}
/**
 * 给java层返回native层的错误信息
 * @param env
 * @param file      失败时的类名
 * @param func      失败时的函数
 * @param line      失败时的代码行数
 * @param msg       函数执行失败的原因
 * @return          返回失败原因的string对象，执行出错时返回NULL
 */
jstring getErrorInfo(JNIEnv* env,char *file, const char *func, int line, char *msg) {
    jstring errinfoStr = NULL;
    int errinfo_len = strlen(file) + strlen(func) + sizeof(line) + strlen(msg);
    char *errinfo = (char *) malloc(errinfo_len + 1);
    if(errinfo == NULL)
        return NULL;
    sprintf(errinfo,"error: %s,%s,%d,%s",file,func,line, msg);
    errinfoStr = charsTojstring(env, errinfo);
    free(errinfo);
    return errinfoStr;
}
//byte数组转化成char数组
char* ConvertJByteaArrayToChars(JNIEnv *env, jbyteArray bytearray)
{
    char *chars = NULL;
    jbyte *bytes;
    bytes = env->GetByteArrayElements(bytearray, 0);
    int chars_len = env->GetArrayLength(bytearray);
    chars = new char[chars_len + 1];
    memset(chars,0,chars_len + 1);
    memcpy(chars, bytes, chars_len);
    chars[chars_len] = 0;

    env->ReleaseByteArrayElements(bytearray, bytes, 0);

    return chars;
}