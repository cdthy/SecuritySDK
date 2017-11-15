#include <jni.h>
#include <string>
#include <basicutil.h>
extern "C"
JNIEXPORT jstring JNICALL
Java_com_nstl_securitysdkcore_SecuritySDKInit_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
