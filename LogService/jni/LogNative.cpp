#include <jni.h>
#include <android/log.h>

namespace com_marakana_android_lognative {

	void log(int priority, const char *tag, const char *text) {
		__android_log_write(priority, tag, text);
	}

	static void log_jni(JNIEnv *env, jclass clazz, jint priority, jstring tag, jstring text) {
		// Convert java strings to C
		const char* c_tag = env->GetStringUTFChars(tag, 0);
		const char* c_text = env->GetStringUTFChars(text, 0);

		// Check that the length of tag and message is greater than 0
		if( env->GetStringUTFLength(tag) == 0 || env->GetStringUTFLength(text) == 0 ) {
			jclass exception = env->FindClass("java/lang/IllegalArgumentException");
			env->ThrowNew(exception, "Neither tag nor text can be null");
		}

		if( !env->ExceptionCheck() ) {
			// Log it
			log(priority, c_tag, c_text);
		}

		// Release the strings
		env->ReleaseStringUTFChars(tag, c_tag);
		env->ReleaseStringUTFChars(text, c_text);
	}

	static JNINativeMethod method_table[] {
		{ "logN", "(ILjava/lang/String;Ljava/lang/String;)V", (void*)log_jni }
	};
}

using namespace com_marakana_android_lognative;

extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    } else {
        jclass clazz = env->FindClass("com/marakana/android/lognative/LogLib");
        if (clazz) {
                jint ret = env->RegisterNatives(clazz, method_table, sizeof(method_table) / sizeof(method_table[0]));
                env->DeleteLocalRef(clazz);
                return ret == 0 ? JNI_VERSION_1_6 : JNI_ERR;
        } else {
                return JNI_ERR;
        }
    }
}

