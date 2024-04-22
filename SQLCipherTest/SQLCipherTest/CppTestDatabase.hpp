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

}

#endif
