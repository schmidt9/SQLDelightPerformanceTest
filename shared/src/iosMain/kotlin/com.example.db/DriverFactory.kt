package com.example.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(context: Any?): SqlDriver {
        return NativeSqliteDriver(TestDatabase.Schema, "test.db")
    }
}