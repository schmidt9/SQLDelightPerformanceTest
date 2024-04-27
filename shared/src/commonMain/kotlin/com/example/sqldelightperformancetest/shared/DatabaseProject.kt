package com.example.sqldelightperformancetest.shared

data class DatabaseProject(
    var projectId: Long = 0,
    var name: String = "",
    var created: Long = 0,
    var updateTime: Long = 0,
    var isActive: Boolean = false
)