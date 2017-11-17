//
// Created by plldzy on 17-11-15.
//基础工具类:1)字符串jstring处理；2)
//
#include <jni.h>
#include <string>
#include <stdlib.h>
#ifndef SECURITYSDK_BASICUTIL_H
#define SECURITYSDK_BASICUTIL_H

char* jstringToChar(JNIEnv* env, jstring jstr);
jstring charsTojstring(JNIEnv* env, char* str);
jstring getErrorInfo(JNIEnv* env,char *file, const char *func, int line, char *msg);
char* ConvertJByteaArrayToChars(JNIEnv *env, jbyteArray bytearray);

#endif //SECURITYSDK_BASICUTIL_H

