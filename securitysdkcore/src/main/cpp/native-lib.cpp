#include <jni.h>
#include <string>
#include "basicutil.h"
extern "C"
JNIEXPORT jsize JNICALL
Java_com_nstl_securitysdkcore_NativeCoreUtil_debugPresent(
        JNIEnv* env,
        jobject /* this */) {

    return 1;
}
JNIEXPORT jsize JNICALL Java_com_nstl_securitysdkcore_NativeCoreUtil_runInEmulator(
        JNIEnv* env,
jobject /* this */) {
    return 1;
}
JNIEXPORT void JNICALL
Java_com_nstl_securitysdkcore_NativeCoreUtil_rePackage(
        JNIEnv* env,
jobject /* this */, jobject verify) {

}
JNIEXPORT jsize JNICALL
Java_com_nstl_securitysdkcore_NativeCoreUtil_detectInject(
        JNIEnv* env,
jobject /* this */) {
return 1;
}