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
        val identifiersQuery = queries.fetchProjectIdentifiers()
        val identifiersList = identifiersQuery.executeAsList()

        val projects = identifiersList.map {
            val projectQuery = queries.fetchProject(it)
            // FIXME: fix crash NullPointerException due to too big image size (~3.4mb)
            // Window is full: requested allocation 3427723 bytes, free space 2096650 bytes, window size 2097152 bytes
            // https://stackoverflow.com/questions/20094421/cursor-window-window-is-full
            // try https://github.com/andpor/react-native-sqlite-storage/issues/364#issuecomment-526423153
            val project = projectQuery.executeAsOne()

            return@map project
        }

        return projects
    }

}