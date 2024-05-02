package com.example.sqldelightperformancetest.shared

import androidx.compose.ui.graphics.drawscope.DrawContext
import comexampledb.Project

expect fun createNativeProjects(context: Any?)

expect fun fetchNativeProjects() : ArrayList<Project>