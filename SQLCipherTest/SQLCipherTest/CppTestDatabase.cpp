#include "CppTestDatabase.hpp"
#include "TestDatabase.hpp"

using namespace test;

jclass javaClassRef;

JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_createProjects(
        JNIEnv *env,
        jclass cls)
{
    auto database = TestDatabase("/data/data/com.example.sqldelightperformancetest.androidApp/databases/test.db");

    database.createProjects();
}

JNIEXPORT jobject
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

    // get DatabaseProject class
    javaClassRef = (jclass) env->NewGlobalRef(env->FindClass("com/example/sqldelightperformancetest/androidApp/DatabaseProject"));
    jmethodID class_constructor = env->GetMethodID(javaClassRef, "<init>", "()V");

    auto projects = database.fetchProjects();

    for (auto &project : projects) {
        // create DatabaseProject object
        jobject class_object = env->NewObject(javaClassRef, class_constructor, "");

        // set name property
        jstring name = env->NewStringUTF(project.name.c_str());
        jmethodID setter = env->GetMethodID(javaClassRef, "setName", "(Ljava/lang/String;)V");
        env->CallVoidMethod(class_object, setter, name);

        // TODO: complete

        // add object to array
        jmethodID java_add_method = env->GetMethodID(java_util_class, "add", "(Ljava/lang/Object;)Z");
        env->CallBooleanMethod(java_util_object, java_add_method, class_object);
    }

    env->DeleteGlobalRef(javaClassRef);

    return java_util_object;
}
