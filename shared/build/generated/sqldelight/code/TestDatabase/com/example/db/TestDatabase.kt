package com.example.db

import com.example.db.shared.newInstance
import com.example.db.shared.schema
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import comexampledb.DataQueries

interface TestDatabase : Transacter {
  val dataQueries: DataQueries

  companion object {
    val Schema: SqlDriver.Schema
      get() = TestDatabase::class.schema

    operator fun invoke(driver: SqlDriver): TestDatabase = TestDatabase::class.newInstance(driver)}
}
