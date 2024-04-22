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

    database.fetchProjects();
}
