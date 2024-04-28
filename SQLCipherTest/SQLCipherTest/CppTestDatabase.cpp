#include "CppTestDatabase.hpp"
#include "TestDatabase.hpp"

using namespace test;

static jclass databaseProjectRef;

JNIEXPORT jint JNICALL JNI_OnLoad (JavaVM * vm, void * reserved) {
    JNIEnv* env;

    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    auto cls = env->FindClass("comexampledb/Project");

    if (cls == NULL) {
        return JNI_ERR;
    }

    databaseProjectRef = (jclass) env->NewGlobalRef(cls);

    return JNI_VERSION_1_6;
}

void
createProjects(const std::string &databasePath) {
    auto database = TestDatabase(databasePath);

    database.createProjects();
}

jobject
fetchProjects(JNIEnv *env, const std::string &databasePath) {
    auto database = TestDatabase(databasePath);

    // https://medium.com/@TSG/how-to-obtain-an-arraylist-with-self-defined-java-kotlin-data-class-from-native-c-processing-8ee94ee86c25

    // create ArrayList
    auto arrayListClass = env->FindClass("java/util/ArrayList");
    auto arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    auto arrayListObject = env->NewObject(arrayListClass, arrayListConstructor);

    auto databaseProjectConstructor = env->GetMethodID(databaseProjectRef, "<init>","(JLjava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;)V");

    auto projects = database.fetchProjects();

    for (auto &project : projects) {
        auto projectId = project.id;
        auto name = env->NewStringUTF(project.name.c_str());
        auto created = project.created;
        auto updateTime = project.updateTime;
        auto isActive = (jlong) project.isActive;

        // create DatabaseProject object
        auto databaseProjectObject = env->NewObject(databaseProjectRef, databaseProjectConstructor, projectId, name, created, updateTime, isActive);

        // add object to array
        auto arrayListAddMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        env->CallBooleanMethod(arrayListObject, arrayListAddMethod, databaseProjectObject);

        // cleanup
        env->DeleteLocalRef(databaseProjectObject);
        env->DeleteLocalRef(name);
    }

    return arrayListObject;
}

extern "C" JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_createProjects(
        JNIEnv *env,
        jclass cls)
{
    createProjects("/data/data/com.example.sqldelightperformancetest.androidApp/databases/test.db");
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
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_createProjects(JNIEnv *env, jclass clazz) {
    createProjects("/data/data/com.example.sqldelightperformancetest.shared/databases/test.db");
}
extern "C" // TODO: refactor
JNIEXPORT jobject JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_fetchProjects(JNIEnv *env, jclass clazz) {
    return fetchProjects(env, "/data/data/com.example.sqldelightperformancetest.shared/databases/test.db");
}