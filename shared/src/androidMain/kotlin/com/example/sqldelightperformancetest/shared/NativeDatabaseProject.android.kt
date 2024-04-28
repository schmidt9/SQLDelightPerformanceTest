package com.example.sqldelightperformancetest.shared

import comexampledb.Project

actual fun fetchNativeProjects(): ArrayList<Project> = CppTestDatabase().fetchProjects()

actual fun createNativeProjects() {
    CppTestDatabase().createProjects()
}