package com.example.sqldelightperformancetest.shared

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun measureTimeMillis(block: () -> Unit): Long {
    val startDate = NSDate()
    block()
    val endDate = NSDate()

    return ((endDate.timeIntervalSince1970 - startDate.timeIntervalSince1970) * 1000).toLong()
}