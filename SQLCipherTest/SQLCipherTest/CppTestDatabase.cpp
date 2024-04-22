#include "CppTestDatabase.hpp"

#include "TestDatabase.hpp"

using namespace test;

JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_createProjects(
        JNIEnv *env,
        jclass cls)
{
    auto database = TestDatabase(""); // TODO: fix

    database.createProjects();
}

JNIEXPORT void
Java_com_example_sqldelightperformancetest_androidApp_CppTestDatabase_fetchProjects(
        JNIEnv *env,
        jclass cls)
{
    auto database = TestDatabase(""); // TODO: fix

    database.fetchProjects();
}
