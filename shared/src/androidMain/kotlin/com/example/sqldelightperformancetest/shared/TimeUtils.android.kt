package com.example.sqldelightperformancetest.shared

actual fun measureTimeMillis(block: () -> Unit): Long = kotlin.system.measureTimeMillis(block)
