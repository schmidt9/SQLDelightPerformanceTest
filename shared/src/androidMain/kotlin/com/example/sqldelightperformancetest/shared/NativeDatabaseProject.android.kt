package com.example.sqldelightperformancetest.shared

import android.content.Context
import comexampledb.Project
import java.io.File

actual fun fetchNativeProjects(): ArrayList<Project> = CppTestDatabase().fetchProjects()

actual fun createNativeProjects(context: Any?, count: Int) {
    // create databases directory to avoid sqlite3_open_v2 error on C++ side
    // https://stackoverflow.com/a/12001593/3004003
    val ctx = context as Context
    val databasePath = File(ctx.applicationInfo.dataDir + "/databases")
    databasePath.mkdirs()

    CppTestDatabase().createProjects(count)
}