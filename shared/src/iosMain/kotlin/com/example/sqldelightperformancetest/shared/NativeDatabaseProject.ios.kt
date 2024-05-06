package com.example.sqldelightperformancetest.shared

import TestDatabaseInterop.TDProject
import TestDatabaseInterop.TDTestDatabaseBridge
import comexampledb.Project
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent
import platform.darwin.nil

@OptIn(ExperimentalForeignApi::class)
actual fun fetchNativeProjects(): ArrayList<Project> {
    val databasePath = databasePath()

    val objcProjects = TDTestDatabaseBridge.fetchProjectsWithDatabasePath(databasePath)
    val projects = objcProjects!!.map {
        it as TDProject

        Project(
            _id = it.projectId,
            name = it.name,
            created = it.created,
            update_time = it.updateTime,
            is_active = if (it.isActive) 1 else 0
        )
    } as ArrayList

    return projects
}

@OptIn(ExperimentalForeignApi::class)
actual fun createNativeProjects(count: Int) {
    val databasePath = databasePath()
    TDTestDatabaseBridge.createProjectsWithDatabasePath(databasePath)
}

actual fun databaseDirectoryPath(): String {
    val fileManager = NSFileManager.defaultManager
    val userDirectoryUrl = fileManager.URLsForDirectory(NSApplicationSupportDirectory, NSUserDomainMask).first() as NSURL

    return userDirectoryUrl.URLByAppendingPathComponent("databases")!!.path!!
}

@OptIn(ExperimentalForeignApi::class)
actual fun databasePath(): String {
    return databaseDirectoryPath() + "/test.db"
}