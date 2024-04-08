package com.example.db

import comexampledb.Project

class DatabaseTest(context: Any? = null) {

    private var database: TestDatabase

    init {
        val factory = DriverFactory()
        val driver = factory.createDriver(context)

        database = TestDatabase(driver)
    }

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