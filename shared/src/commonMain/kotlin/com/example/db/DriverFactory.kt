package com.example.db

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {
    expect fun createDriver(): SqlDriver
}