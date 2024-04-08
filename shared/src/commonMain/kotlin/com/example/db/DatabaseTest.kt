package com.example.db

import comexampledb.Project

class DatabaseTest {

    fun createProjects(factory: DriverFactory) {
        val driver = factory.createDriver()
        val database = TestDatabase(driver)
        val queries = database.dataQueries

        queries.clearProjectsTable()

        database.transaction {
            for (i in 0 until 100000) {
                queries.createProject("Project $i")
            }
        }
    }

    fun fetchProjects(factory: DriverFactory): List<Project> {
        val driver = factory.createDriver()
        val database = TestDatabase(driver)

        return database.dataQueries.fetchProjects().executeAsList()
    }

}