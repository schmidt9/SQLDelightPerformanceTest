package com.example.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory actual constructor() {
    actual fun createDriver(context: Any?): SqlDriver {
        val appContext = context as Context
        return AndroidSqliteDriver(TestDatabase.Schema, appContext, "test.db")
    }
}