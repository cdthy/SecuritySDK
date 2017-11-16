#include <jni.h>
#include <string>
#include <unistd.h>
#include "Util.h"
#include "SimulatorDetected.h"
extern "C"
JNIEXPORT jint JNICALL
Java_com_nstl_securitysdkcore_NativeCoreUtil_debugPresent(JNIEnv *env, jobject instance) {

    // TODO
    int BUF_SIZE = 1024;
    char file_path[1024] = "\0";
    char buffer[1024] = "\0";
    //get app pid
    int pid = getpid();
    sprintf(file_path, "/proc/%d/status", pid);
    FILE *fp = fopen(file_path,"r");
    if(fp == NULL){
        return -2;
    }else{
        while(fgets(buffer,BUF_SIZE,fp)){
            if (strncmp(buffer, "TracerPid", 9) == 0) {
                int statue = atoi(&buffer[10]);
                if (statue != 0) {
                    fclose(fp);
                    //返回错误的返回值
                    return -1;
                }
                break;
            }
        }
        fclose(fp);
    }
    return 0;

}

extern "C"
JNIEXPORT jint JNICALL
Java_com_nstl_securitysdkcore_NativeCoreUtil_runInEmulator(JNIEnv *env, jobject instance,
                                                           jobject mContext) {

    // TODO
    return simulatorDetected(env,mContext,30);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_nstl_securitysdkcore_NativeCoreUtil_rePackage(JNIEnv *env, jobject instance,
                                                       jobject verifyListener) {

    // TODO

}

extern "C"
JNIEXPORT jint JNICALL
Java_com_nstl_securitysdkcore_NativeCoreUtil_detectInject(JNIEnv *env, jobject instance) {

    // TODO

}