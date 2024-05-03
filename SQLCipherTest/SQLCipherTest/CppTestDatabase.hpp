#ifndef CppTestDatabase_hpp
#define CppTestDatabase_hpp

#include <jni.h>
#include <thread>

extern "C" {

JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_createProjects(
        JNIEnv *,
        jclass);

JNIEXPORT jobject
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_fetchProjects(
        JNIEnv *,
        jclass);

extern "C"
JNIEXPORT void JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_createProjects(JNIEnv *env,
                                                                                 jclass clazz, jint count);
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_fetchProjects(JNIEnv *env,
                                                                                jclass clazz);

}

#endif

