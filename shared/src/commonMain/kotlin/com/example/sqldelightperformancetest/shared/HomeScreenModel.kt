package com.example.sqldelightperformancetest.shared

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.db.DatabaseTest
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.RunningTest(false)) {

    sealed class State {
        data class RunningTest(val isRunning: Boolean) : State()
        data class Result(val logString: String) : State()
    }

    fun runRecordsTest() {
        screenModelScope.launch(Dispatchers.IO) {
            mutableState.value = State.RunningTest(isRunning = true)
            val logString = fetchTestResults()
            mutableState.value = State.Result(logString)
        }
    }

    private fun fetchTestResults(): String {
        val projectsCount = 100_000
        val databaseTest = DatabaseTest()
        val builder = StringBuilder()

        val sqlDelightCreateProjectsTime = measureTimeMillis {
            databaseTest.createProjects(projectsCount)
        }

        val sqlDelightFetchProjectsTime = measureTimeMillis {
            databaseTest.fetchProjects()
        }

        val sqlite3CreateProjectsTime = measureTimeMillis {
            createNativeProjects(projectsCount)
        }

        val sqlite3FetchProjectsTime = measureTimeMillis {
            fetchNativeProjects()
        }

        builder.append(if (isDebug) "\nRunning Debug" else "\nRunning Release").append("\n\n")

        builder.append("Test projects count: $projectsCount\n\n")

        builder.append("SQLDelight create projects: ${sqlDelightCreateProjectsTime / 1000.0}\n")
        builder.append("SQLDelight fetch projects: ${sqlDelightFetchProjectsTime / 1000.0}\n\n")

        builder.append("sqlite3 (SQLCipher) create projects: ${sqlite3CreateProjectsTime / 1000.0}\n")
        builder.append("sqlite3 (SQLCipher) fetch projects: ${sqlite3FetchProjectsTime / 1000.0}")

        Napier.d("$builder")

        return builder.toString()
    }

}