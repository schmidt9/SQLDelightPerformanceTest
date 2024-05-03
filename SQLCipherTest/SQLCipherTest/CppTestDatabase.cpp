#include <android/log.h>
#include "CppTestDatabase.hpp"
#include "TestDatabase.hpp"

using namespace test;

void
createProjects(const std::string &databasePath, int count) {
    auto database = TestDatabase(databasePath);

    database.createProjects(count);

    if (auto error = database.getLastError(); !error.empty()) {
        __android_log_write(ANDROID_LOG_ERROR, "CppTestDatabase", error.c_str());
    }
}

jobject
fetchProjects(JNIEnv *env, const std::string &databasePath) {
    auto database = TestDatabase(databasePath);

    // https://medium.com/@TSG/how-to-obtain-an-arraylist-with-self-defined-java-kotlin-data-class-from-native-c-processing-8ee94ee86c25

    // create ArrayList
    auto arrayListClass = env->FindClass("java/util/ArrayList");
    auto arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    auto arrayListObject = env->NewObject(arrayListClass, arrayListConstructor);

    auto projects = database.fetchProjects();

    for (auto &project : projects) {
        auto projectId = (jlong) project.id;
        auto name = env->NewStringUTF(project.name.c_str());

        auto longClass = env->FindClass("java/lang/Long");
        auto valueOfMethodId = env->GetStaticMethodID(longClass, "valueOf", "(J)Ljava/lang/Long;");
        auto created = env->CallStaticObjectMethod(longClass, valueOfMethodId, (jlong) project.created);
        auto updateTime = env->CallStaticObjectMethod(longClass, valueOfMethodId, (jlong) project.updateTime);
        auto isActive = env->CallStaticObjectMethod(longClass, valueOfMethodId, (jlong) project.isActive);

        auto cls = env->FindClass("comexampledb/Project");
        auto databaseProjectConstructor = env->GetMethodID(
            cls,
            "<init>",
            "(JLjava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;)V");

        // create DatabaseProject object
        auto databaseProjectObject = env->NewObject(
            cls,
            databaseProjectConstructor,
            projectId,
            name,
            created,
            updateTime,
            isActive);

        // add object to array
        auto arrayListAddMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        env->CallBooleanMethod(arrayListObject, arrayListAddMethod, databaseProjectObject);

        // cleanup
        env->DeleteLocalRef(name);
        env->DeleteLocalRef(longClass);
        env->DeleteLocalRef(created);
        env->DeleteLocalRef(updateTime);
        env->DeleteLocalRef(isActive);
        env->DeleteLocalRef(cls);
        env->DeleteLocalRef(databaseProjectObject);
    }


    return arrayListObject;
}

extern "C" JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_createProjects(
        JNIEnv *env,
        jclass cls)
{
    createProjects("/data/data/com.example.sqldelightperformancetest.androidApp/databases/test.db", 0); // TODO: remove or fix
}

extern "C" JNIEXPORT jobject
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_fetchProjects(
        JNIEnv *env,
        jclass cls)
{
    return fetchProjects(env, "/data/data/com.example.sqldelightperformancetest.androidApp/databases/test.db");
}

extern "C" // TODO: refactor
JNIEXPORT void JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_createProjects(JNIEnv *env, jclass clazz, jint count) {
    createProjects("/data/data/com.example.sqldelightperformancetest.shared/databases/test.db", count);
}
extern "C" // TODO: refactor
JNIEXPORT jobject JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_fetchProjects(JNIEnv *env, jclass clazz) {
    return fetchProjects(env, "/data/data/com.example.sqldelightperformancetest.shared/databases/test.db");
}