package com.example.sqldelightperformancetest.shared

import TestDatabaseInterop.TDTestDatabaseBridge
import comexampledb.Project
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent
import platform.darwin.nil

@OptIn(ExperimentalForeignApi::class)
actual fun fetchNativeProjects(context: Any?): ArrayList<Project> {
    val databasePath = databasePath(nil)

    return TDTestDatabaseBridge().fetchProjectsWithDatabasePath(databasePath)
}

actual fun createNativeProjects(context: Any?, count: Int) {
    TODO("Not yet implemented")
}

actual fun databaseDirectoryPath(context: Any?): String {
    val fileManager = NSFileManager.defaultManager
    val userDirectoryUrl = fileManager.URLsForDirectory(NSApplicationSupportDirectory, NSUserDomainMask).first() as NSURL

    return userDirectoryUrl.URLByAppendingPathComponent("databases")!!.path!!
}

@OptIn(ExperimentalForeignApi::class)
actual fun databasePath(context: Any?): String {
    val databaseDirectoryURL = NSURL(databaseDirectoryPath(nil))

    return databaseDirectoryURL.URLByAppendingPathComponent("test.db")!!.path!!
}