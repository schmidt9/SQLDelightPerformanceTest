package com.example.sqldelightperformancetest.shared

import androidx.compose.ui.graphics.drawscope.DrawContext
import comexampledb.Project

expect fun createNativeProjects(context: Any?, count: Int)

expect fun fetchNativeProjects() : ArrayList<Project>