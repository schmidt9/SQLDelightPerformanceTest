package com.example.sqldelightperformancetest.shared

import comexampledb.Project

expect fun databaseDirectoryPath(): String
expect fun databasePath(): String

expect fun createNativeProjects(count: Int)

expect fun createNativeImageProjects(count: Int, imageData: ByteArray)

expect fun fetchNativeProjects() : ArrayList<Project>