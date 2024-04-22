package com.example.sqldelightperformancetest.androidApp

data class DatabaseProject(
    var projectId: Int = 0,
    var name: String = "",
    var created: Int = 0,
    var updateTime: Int = 0,
    var isActive: Boolean = false
)