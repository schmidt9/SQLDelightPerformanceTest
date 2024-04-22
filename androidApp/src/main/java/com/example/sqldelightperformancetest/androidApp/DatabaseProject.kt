package com.example.sqldelightperformancetest.androidApp

data class DatabaseProject(
    var projectId: Int,
    var name: String,
    var created: Int,
    var updateTime: Int,
    var isActive: Boolean
)