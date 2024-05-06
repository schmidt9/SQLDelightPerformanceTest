#ifndef CppTestDatabase_hpp
#define CppTestDatabase_hpp

#include <jni.h>
#include <thread>

extern "C" {

JNIEXPORT void JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_createProjects(JNIEnv *env,
                                                                                 jclass clazz, jstring databasePath, jint count);
JNIEXPORT void JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_createImageProjects(JNIEnv *env,
                                                                                      jobject thiz,
                                                                                      jstring databasePath,
                                                                                      jint count,
                                                                                      jbyteArray imageData);

JNIEXPORT jobject JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_fetchProjects(JNIEnv *env,
                                                                                jclass clazz, jstring databasePath);

}

#endif

