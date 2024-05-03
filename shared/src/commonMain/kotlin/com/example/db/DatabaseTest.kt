package com.example.db

import comexampledb.Project

class DatabaseTest(context: Any?) {

    private var database: TestDatabase

    constructor() : this(null)

    init {
        val factory = DriverFactory()
        val driver = factory.createDriver(context)

        database = TestDatabase(driver)
    }

    fun createProjects(count: Int) {
        val queries = database.dataQueries

        queries.clearProjectsTable()

        database.transaction {
            for (i in 0 until count) {
                queries.createProject("Project $i")
            }
        }
    }

    fun fetchProjects(): List<Project> =
        database.dataQueries.fetchProjects().executeAsList()

}