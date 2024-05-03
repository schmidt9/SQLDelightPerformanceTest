package com.example.sqldelightperformancetest.shared

import comexampledb.Project

expect fun databaseDirectoryPath(context: Any?): String
expect fun databasePath(context: Any?): String

expect fun createNativeProjects(context: Any?, count: Int)

expect fun fetchNativeProjects(context: Any?) : ArrayList<Project>