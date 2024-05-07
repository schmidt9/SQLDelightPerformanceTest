package com.example.db

import comexampledb.Project

class DatabaseTest() {

    private var database: TestDatabase

    init {
        val factory = DriverFactory()
        val driver = factory.createDriver()

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

    fun createImageProjects(count: Int, image: ByteArray) {
        val queries = database.dataQueries

        queries.clearProjectsTable()

        database.transaction {
            for (i in 0 until count) {
                queries.createImageProject("Project $i", image)
            }
        }
    }

    fun fetchProjects(): List<Project> {
        val queries = database.dataQueries
        val query = queries.fetchProjects()
        val projectsList = query.executeAsList()

        return projectsList
    }

}