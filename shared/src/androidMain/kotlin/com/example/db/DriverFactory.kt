package com.example.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory actual constructor() {

    private lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
    }

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TestDatabase.Schema, context, "test.db")
    }
}