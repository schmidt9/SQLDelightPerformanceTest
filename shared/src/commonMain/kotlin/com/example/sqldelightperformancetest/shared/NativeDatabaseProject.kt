package com.example.sqldelightperformancetest.shared

import comexampledb.Project

expect fun createNativeProjects()

expect fun fetchNativeProjects() : ArrayList<Project>