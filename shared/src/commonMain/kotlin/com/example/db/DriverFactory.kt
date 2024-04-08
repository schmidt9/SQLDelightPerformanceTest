package com.example.db

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory() {
    fun createDriver(context: Any? = null): SqlDriver
}