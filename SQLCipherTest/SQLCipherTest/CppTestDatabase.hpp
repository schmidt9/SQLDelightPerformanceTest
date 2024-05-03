#ifndef CppTestDatabase_hpp
#define CppTestDatabase_hpp

#include <jni.h>
#include <thread>

extern "C" {

JNIEXPORT void JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_createProjects(JNIEnv *env,
                                                                                 jclass clazz, jint count);
JNIEXPORT jobject JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_fetchProjects(JNIEnv *env,
                                                                                jclass clazz);

}

#endif

