package com.example.db

import android.database.CursorWindow
import android.os.Build
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.sqldelightperformancetest.shared.AppContext
import java.lang.reflect.Field

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val appContext = AppContext.get()

        val windowSize = 1024 * 1024 * 10 // 10mb

        // fix for Android below 9 API 28
        // https://github.com/andpor/react-native-sqlite-storage/issues/364#issuecomment-526423153
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            setCursorWindowSize(windowSize)
        }

        // https://github.com/cashapp/sqldelight/blob/master/drivers/android-driver/src/main/java/app/cash/sqldelight/driver/android/AndroidSqliteDriver.kt
        return AndroidSqliteDriver(
            TestDatabase.Schema,
            appContext,
            "test.db",
            cacheSize = 1,
            // fix CursorWindow (working since Android 9 API 28)
            // https://github.com/cashapp/sqldelight/pull/4804
            windowSizeBytes = windowSize.toLong())
    }

    private fun setCursorWindowSize(size: Int) {
        try {
            val field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, size)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}