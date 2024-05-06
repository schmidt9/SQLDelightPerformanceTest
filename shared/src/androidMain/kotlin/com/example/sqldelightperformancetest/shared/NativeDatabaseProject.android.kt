package com.example.sqldelightperformancetest.shared

import comexampledb.Project
import java.io.File

actual fun fetchNativeProjects(): ArrayList<Project> {
    return CppTestDatabase().fetchProjects(databasePath())
}

actual fun createNativeProjects(count: Int) {
    // create databases directory to avoid sqlite3_open_v2 error on C++ side
    // https://stackoverflow.com/a/12001593/3004003
    val databaseDirectoryFile = File(databaseDirectoryPath())
    databaseDirectoryFile.mkdirs()
    val databasePath = databasePath()

    CppTestDatabase().createProjects(databasePath, count)
}

actual fun createNativeImageProjects(count: Int, imageData: ByteArray) {
    // create databases directory to avoid sqlite3_open_v2 error on C++ side
    // https://stackoverflow.com/a/12001593/3004003
    val databaseDirectoryFile = File(databaseDirectoryPath())
    databaseDirectoryFile.mkdirs()
    val databasePath = databasePath()

    CppTestDatabase().createImageProjects(databasePath, count, imageData)
}

actual fun databaseDirectoryPath(): String {
    val context = AppContext.get()
    return context.applicationInfo.dataDir + "/databases"
}

actual fun databasePath(): String {
    return databaseDirectoryPath() + "/test.db"
}