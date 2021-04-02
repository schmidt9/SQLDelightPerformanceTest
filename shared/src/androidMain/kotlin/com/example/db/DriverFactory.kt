package com.example.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory actual constructor() {

    private lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
    }

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TestDatabase.Schema, context, "test.db")
    }
}