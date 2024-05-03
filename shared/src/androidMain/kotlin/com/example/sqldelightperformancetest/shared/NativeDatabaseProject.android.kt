package com.example.sqldelightperformancetest.shared

import android.content.Context
import comexampledb.Project
import java.io.File

actual fun fetchNativeProjects(context: Any?): ArrayList<Project> {
    return CppTestDatabase().fetchProjects(databasePath(context))
}

actual fun createNativeProjects(context: Any?, count: Int) {
    // create databases directory to avoid sqlite3_open_v2 error on C++ side
    // https://stackoverflow.com/a/12001593/3004003
    val databaseDirectoryFile = File(databaseDirectoryPath(context))
    databaseDirectoryFile.mkdirs()
    val databasePath = databasePath(context)

    CppTestDatabase().createProjects(databasePath, count)
}

actual fun databaseDirectoryPath(context: Any?): String {
    val ctx = context as Context
    return ctx.applicationInfo.dataDir + "/databases"
}

actual fun databasePath(context: Any?): String {
    return databaseDirectoryPath(context) + "/test.db"
}