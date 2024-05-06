package com.example.sqldelightperformancetest.shared

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.db.DatabaseTest
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlin.math.log

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.RunningTest(false)) {

    sealed class State {
        data class RunningTest(val isRunning: Boolean) : State()
        data class Result(val logString: String) : State()
    }

    enum class TestMode {
        SQLDELIGHT,
        SQLITE3,
        ALL
    }

    private val projectsCount = 100_000

    fun runTests(mode: TestMode) {
        screenModelScope.launch(Dispatchers.IO) {
            val builder = StringBuilder()
            builder.append(if (isDebug) "\nRunning Debug" else "\nRunning Release").append("\n\n")
            builder.append("Test projects count: $projectsCount\n\n")

            mutableState.value = State.RunningTest(isRunning = true)

            val logString: String = when (mode) {
                TestMode.SQLDELIGHT -> runSqlDelightTest()
                TestMode.SQLITE3 -> runSqlite3Test()
                TestMode.ALL -> runSqlDelightTest() + runSqlite3Test()
            }

            builder.append(logString)

            Napier.d("$builder")

            mutableState.value = State.Result(builder.toString())
        }
    }

    private fun runSqlDelightTest(): String {
        val databaseTest = DatabaseTest()
        val builder = StringBuilder()

        val sqlDelightCreateProjectsTime = measureTimeMillis {
            databaseTest.createProjects(projectsCount)
        }

        val sqlDelightFetchProjectsTime = measureTimeMillis {
            databaseTest.fetchProjects()
        }

        builder.append("SQLDelight create projects: ${sqlDelightCreateProjectsTime / 1000.0}\n")
        builder.append("SQLDelight fetch projects: ${sqlDelightFetchProjectsTime / 1000.0}\n\n")

        return builder.toString()
    }

    private fun runSqlite3Test(): String {
        val builder = StringBuilder()

        val sqlite3CreateProjectsTime = measureTimeMillis {
            createNativeProjects(projectsCount)
        }

        val sqlite3FetchProjectsTime = measureTimeMillis {
            fetchNativeProjects()
        }

        builder.append("sqlite3 (SQLCipher) create projects: ${sqlite3CreateProjectsTime / 1000.0}\n")
        builder.append("sqlite3 (SQLCipher) fetch projects: ${sqlite3FetchProjectsTime / 1000.0}")

        return builder.toString()
    }

}