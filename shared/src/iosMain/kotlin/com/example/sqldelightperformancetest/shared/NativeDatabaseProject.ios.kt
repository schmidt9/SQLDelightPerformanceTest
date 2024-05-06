package com.example.sqldelightperformancetest.shared

import TestDatabaseInterop.TDProject
import TestDatabaseInterop.TDTestDatabaseBridge
import comexampledb.Project
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent
import platform.Foundation.create
import platform.darwin.NSInteger
import platform.posix.memcpy

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
            is_active = if (it.isActive) 1 else 0,
            image = it.imageData?.let { data ->
                ByteArray(data.length.toInt()).apply {
                    usePinned { pinned ->
                        memcpy(pinned.addressOf(0), data.bytes, data.length)
                    }
                }
            }
        )
    } as ArrayList

    return projects
}

@OptIn(ExperimentalForeignApi::class)
actual fun createNativeProjects(count: Int) {
    val databasePath = databasePath()
    TDTestDatabaseBridge.createProjectsWithDatabasePath(databasePath, count.toLong())
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun createNativeImageProjects(count: Int, imageData: ByteArray) {
    val databasePath = databasePath()
    val nsData = memScoped {
        NSData.create(bytes = allocArrayOf(imageData), length = imageData.size.toULong())
    }

    TDTestDatabaseBridge.createProjectsWithDatabasePath(databasePath, count.toLong(), nsData)
}

actual fun databaseDirectoryPath(): String {
    val fileManager = NSFileManager.defaultManager
    val userDirectoryUrl = fileManager.URLsForDirectory(NSApplicationSupportDirectory, NSUserDomainMask).first() as NSURL

    return userDirectoryUrl.URLByAppendingPathComponent("databases")!!.path!!
}

actual fun databasePath(): String {
    return databaseDirectoryPath() + "/test.db"
}