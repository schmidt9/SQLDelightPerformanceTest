#include "CppTestDatabase.hpp"
#include "TestDatabase.hpp"

using namespace test;

extern "C" JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_createProjects(
        JNIEnv *env,
        jclass cls)
{
    auto database = TestDatabase("/data/data/com.example.sqldelightperformancetest.androidApp/databases/test.db");

    database.createProjects();
}

extern "C" JNIEXPORT jobject
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_fetchProjects(
        JNIEnv *env,
        jclass cls)
{
    auto database = TestDatabase("/data/data/com.example.sqldelightperformancetest.androidApp/databases/test.db");

    // https://medium.com/@TSG/how-to-obtain-an-arraylist-with-self-defined-java-kotlin-data-class-from-native-c-processing-8ee94ee86c25

    // create ArrayList
    auto arrayListClass = env->FindClass("java/util/ArrayList");
    auto arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    auto arrayListObject = env->NewObject(arrayListClass, arrayListConstructor);

    // get DatabaseProject class
    auto databaseProjectRef = (jclass) env->NewLocalRef(env->FindClass("com/example/sqldelightperformancetest/androidApp/DatabaseProject"));
    auto databaseProjectConstructor = env->GetMethodID(databaseProjectRef, "<init>", "()V");

    auto projects = database.fetchProjects();

    for (auto &project : projects) {
        // create DatabaseProject object
        auto databaseProjectObject = env->NewObject(databaseProjectRef, databaseProjectConstructor);

        // set projectId
        auto projectId = project.id;
        auto projectIdSetter = env->GetMethodID(databaseProjectRef, "setProjectId", "(J)V");
        env->CallVoidMethod(databaseProjectObject, projectIdSetter, projectId);

        // set name
        auto name = env->NewStringUTF(project.name.c_str());
        auto nameSetter = env->GetMethodID(databaseProjectRef, "setName", "(Ljava/lang/String;)V");
        env->CallVoidMethod(databaseProjectObject, nameSetter, name);

        // set created
        auto created = project.created;
        auto createdSetter = env->GetMethodID(databaseProjectRef, "setCreated", "(J)V");
        env->CallVoidMethod(databaseProjectObject, createdSetter, created);

        // set updateTime
        auto updateTime = project.updateTime;
        auto updateTimeSetter = env->GetMethodID(databaseProjectRef, "setUpdateTime", "(J)V");
        env->CallVoidMethod(databaseProjectObject, updateTimeSetter, updateTime);

        // set isActive
        auto isActive = project.isActive;
        auto isActiveSetter = env->GetMethodID(databaseProjectRef, "setActive", "(Z)V");
        env->CallVoidMethod(databaseProjectObject, isActiveSetter, isActive);

        // add object to array
        auto arrayListAddMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        env->CallBooleanMethod(arrayListObject, arrayListAddMethod, databaseProjectObject);

        // cleanup
        env->DeleteLocalRef(databaseProjectObject);
        env->DeleteLocalRef(name);
    }

    // cleanup
    env->DeleteLocalRef(databaseProjectRef);

    return arrayListObject;
}
