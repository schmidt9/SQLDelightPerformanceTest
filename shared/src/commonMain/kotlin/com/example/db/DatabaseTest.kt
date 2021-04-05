package com.example.db

import comexampledb.Project

class DatabaseTest {

    val database = TestDatabase(DriverFactory().createDriver())

    fun createProjects() {
        val queries = database.dataQueries

        queries.clearProjectsTable()

        database.transaction {
            for (i in 0 until 100000) {
                queries.createProject("Project $i")
            }
        }
    }

    fun fetchProjects(): List<Project> {
        return database.dataQueries.fetchProjects().executeAsList()
    }

}