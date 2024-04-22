#include <jni.h>

#include "CppTestDatabase.hpp"
#include "TestDatabase.hpp"

using namespace test;

JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_createProjects(
        JNIEnv *env,
        jclass cls)
{
    auto database = TestDatabase("/data/data/com.example.sqldelightperformancetest.androidApp/databases/test.db");

    database.createProjects();
}

JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_fetchProjects(
        JNIEnv *env,
        jclass cls)
{
    auto database = TestDatabase("/data/data/com.example.sqldelightperformancetest.androidApp/databases/test.db");

    // https://medium.com/@TSG/how-to-obtain-an-arraylist-with-self-defined-java-kotlin-data-class-from-native-c-processing-8ee94ee86c25

    // create ArrayList
    jclass java_util_class = env->FindClass("java/util/ArrayList");
    jmethodID java_util_method_constructor = env->GetMethodID(java_util_class, "<init>", "()V");
    jobject java_util_object = env->NewObject(java_util_class, java_util_method_constructor, "");

    auto projects = database.fetchProjects(); // TODO: complete

    for (auto &project : projects) {
        jclass javaClassRef = (jclass)env-NewGlobalRef(env->FindClass("com.example.sqldelightperformancetest.androidApp.DatabaseProject"));
        jmethodID class_constructor = env->GetMethodID(javaClassRef, "<init>", "()V");
        jobject class_object = env->NewObject(javaClassRef, class_constructor, "");

        jstring name = env->NewStringUTF(project.name.c_str());
    }
}
