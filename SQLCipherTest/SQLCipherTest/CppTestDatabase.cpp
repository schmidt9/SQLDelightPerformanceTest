#include <android/log.h>
#include "CppTestDatabase.hpp"
#include "TestDatabase.hpp"

using namespace test;

std::string
convertJstringToStdString(JNIEnv *env, jstring value)
{
    jboolean isCopy;
    auto convertedValue = (env)->GetStringUTFChars(value, &isCopy);
    auto result = std::string(convertedValue);

    env->ReleaseStringUTFChars(value, convertedValue);

    return result;
}

void
createProjects(JNIEnv *env, jstring databasePath, jint count)
{
    auto _databasePath = convertJstringToStdString(env, databasePath);
    auto database = TestDatabase(_databasePath);

    database.createProjects(count);

    if (auto error = database.getLastError(); !error.empty()) {
        __android_log_write(ANDROID_LOG_ERROR, "CppTestDatabase", error.c_str());
    }
}

jobject
fetchProjects(JNIEnv *env, jstring databasePath)
{
    auto _databasePath = convertJstringToStdString(env, databasePath);
    auto database = TestDatabase(_databasePath);

    // https://medium.com/@TSG/how-to-obtain-an-arraylist-with-self-defined-java-kotlin-data-class-from-native-c-processing-8ee94ee86c25
    auto arrayListClass = env->FindClass("java/util/ArrayList");
    auto arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    auto arrayListObject = env->NewObject(arrayListClass, arrayListConstructor);
    auto arrayListAddMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    auto longClass = env->FindClass("java/lang/Long");
    auto valueOfMethodId = env->GetStaticMethodID(longClass, "valueOf", "(J)Ljava/lang/Long;");

    auto databaseProjectClass = env->FindClass("comexampledb/Project");
    auto databaseProjectConstructor = env->GetMethodID(
        databaseProjectClass,
        "<init>",
        "(JLjava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;)V");

    auto projects = database.fetchProjects();

    for (auto &project : projects) {
        auto projectId = (jlong) project.id;
        auto name = env->NewStringUTF(project.name.c_str());

        auto created = env->CallStaticObjectMethod(longClass, valueOfMethodId, (jlong) project.created);
        auto updateTime = env->CallStaticObjectMethod(longClass, valueOfMethodId, (jlong) project.updateTime);
        auto isActive = env->CallStaticObjectMethod(longClass, valueOfMethodId, (jlong) project.isActive);

        // create DatabaseProject object
        auto databaseProjectObject = env->NewObject(
            databaseProjectClass,
            databaseProjectConstructor,
            projectId,
            name,
            created,
            updateTime,
            isActive);

        // add object to array
        env->CallBooleanMethod(arrayListObject, arrayListAddMethod, databaseProjectObject);

        // cleanup
        env->DeleteLocalRef(name);
        env->DeleteLocalRef(created);
        env->DeleteLocalRef(updateTime);
        env->DeleteLocalRef(isActive);
        env->DeleteLocalRef(databaseProjectObject);
    }

    env->DeleteLocalRef(longClass);
    env->DeleteLocalRef(databaseProjectClass);

    return arrayListObject;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_createProjects(JNIEnv *env, jclass clazz, jstring databasePath, jint count)
{
    createProjects(env, databasePath, count);
}
extern "C" // TODO: refactor
JNIEXPORT jobject JNICALL
Java_com_example_sqldelightperformancetest_shared_CppTestDatabase_fetchProjects(JNIEnv *env, jclass clazz, jstring databasePath)
{
    return fetchProjects(env, databasePath);
}