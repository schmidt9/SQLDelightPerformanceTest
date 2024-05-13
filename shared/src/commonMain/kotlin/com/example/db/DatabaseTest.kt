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

    @OptIn(ExperimentalStdlibApi::class)
    fun fetchProjects(): List<Project> {
        val queries = database.dataQueries
        val identifiersQuery = queries.fetchProjectIdentifiers()
        val identifiersList = identifiersQuery.executeAsList()

        val projects = identifiersList.map {
            val projectWithoutImageQuery = queries.fetchProjectWithoutImage(it)
            val projectImageLengthQuery = queries.fetchProjectImageLength(it)
            // FIXME: fix crash NullPointerException due to too big image size (~3.4mb)
            // Window is full: requested allocation 3427723 bytes, free space 2096650 bytes, window size 2097152 bytes
            // https://stackoverflow.com/questions/20094421/cursor-window-window-is-full
            // try https://github.com/andpor/react-native-sqlite-storage/issues/364#issuecomment-526423153
            val project = projectWithoutImageQuery.executeAsOne()
            val imageLength = projectImageLengthQuery.executeAsOne()
            val length = imageLength.length

            val image = if (length != null && length > 0) {
                val windowSize = 1024 * 50  // 0.5mb
                val windowParts = length.toInt() / windowSize
                var from = 0
                var to = windowSize
                var result: ByteArray? = null

                for (index in 0 until windowParts) {
                    val _from = from.toString()
                    val _to = to.toString()
                    val imagePartQuery = queries.fetchPartProjectImage(_from, _to, it)
                    val image = imagePartQuery.executeAsOne()
                    val bytes = image.substr?.encodeToByteArray()

                    result = bytes?.let { array -> result?.plus(array) } ?: bytes

                    from = to
                    to = if (index + 1 == windowParts) length.toInt() else to + windowSize
                }

                result
            } else {
                null
            }

            val size = image?.size

            return@map Project(
                project._id,
                project.name,
                project.created,
                project.update_time,
                project.is_active,
                image
            )
        }

        return projects
    }

}