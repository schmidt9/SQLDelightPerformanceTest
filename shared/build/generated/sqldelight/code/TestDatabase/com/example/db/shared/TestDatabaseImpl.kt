package com.example.db.shared

import com.example.db.TestDatabase
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import comexampledb.DataQueries
import comexampledb.Project
import kotlin.Any
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.MutableList
import kotlin.reflect.KClass

internal val KClass<TestDatabase>.schema: SqlDriver.Schema
  get() = TestDatabaseImpl.Schema

internal fun KClass<TestDatabase>.newInstance(driver: SqlDriver): TestDatabase =
    TestDatabaseImpl(driver)

private class TestDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), TestDatabase {
  override val dataQueries: DataQueriesImpl = DataQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS project (
          |`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
          |`name` TEXT,
          |`created` INTEGER DEFAULT (strftime('%s','now')),
          |`update_time` INTEGER DEFAULT (strftime('%s','now')),
          |`is_active` INTEGER DEFAULT 0
          |)
          """.trimMargin(), 0)
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ) {
    }
  }
}

private class DataQueriesImpl(
  private val database: TestDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), DataQueries {
  internal val fetchProjects: MutableList<Query<*>> = copyOnWriteList()

  override fun <T : Any> fetchProjects(mapper: (
    _id: Long,
    name: String?,
    created: Long?,
    update_time: Long?,
    is_active: Long?
  ) -> T): Query<T> = Query(1353427657, fetchProjects, driver, "data.sq", "fetchProjects",
      "SELECT * FROM project") { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1),
      cursor.getLong(2),
      cursor.getLong(3),
      cursor.getLong(4)
    )
  }

  override fun fetchProjects(): Query<Project> = fetchProjects { _id, name, created, update_time,
      is_active ->
    Project(
      _id,
      name,
      created,
      update_time,
      is_active
    )
  }

  override fun createProject(projectName: String?) {
    driver.execute(-1095766030, """INSERT INTO project (name) VALUES (?)""", 1) {
      bindString(1, projectName)
    }
    notifyQueries(-1095766030, {database.dataQueries.fetchProjects})
  }
}
