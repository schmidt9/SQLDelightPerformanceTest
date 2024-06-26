package com.example.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.sqldelightperformancetest.shared.AppContext

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val appContext = AppContext.get()
        return AndroidSqliteDriver(TestDatabase.Schema, appContext, "test.db")
    }
}