package com.example.db

import comexampledb.Project

class DatabaseTest {

    constructor(context: Any? = null) {
        val factory = DriverFactory()
        val driver = factory.createDriver(context)

        database = TestDatabase(driver)
    }

    private var database: TestDatabase

    fun createProjects() {
        val queries = database.dataQueries

        queries.clearProjectsTable()

        database.transaction {
            for (i in 0 until 100000) {
                queries.createProject("Project $i")
            }
        }
    }

    fun fetchProjects(): List<Project> =
        database.dataQueries.fetchProjects().executeAsList()

}