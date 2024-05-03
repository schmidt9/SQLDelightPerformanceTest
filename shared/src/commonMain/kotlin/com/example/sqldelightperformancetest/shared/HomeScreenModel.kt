package com.example.sqldelightperformancetest.shared

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.db.DatabaseTest
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

class HomeScreenModel(val context: Any? = null) : StateScreenModel<HomeScreenModel.State>(State.RunningTest(false)) {

    sealed class State {
        data class RunningTest(val isRunning: Boolean) : State()
        data class Result(val records: List<Any>) : State()
    }

    fun runRecordsTest() {
        screenModelScope.launch(Dispatchers.IO) {
            mutableState.value = State.RunningTest(isRunning = true)
            val records = listOf<Any>() // TODO: impl
            fetchTestResults()
            mutableState.value = State.Result(records)
        }
    }

    private fun fetchTestResults(): String {
        val projectsCount = 100_000
        val databaseTest = DatabaseTest(context)
        val builder = StringBuilder()

        val nativeCreateProjectsTime = measureTimeMillis {
            databaseTest.createProjects(projectsCount)
        }

        val nativeFetchProjectsTime = measureTimeMillis {
            databaseTest.fetchProjects()
        }

        val cppCreateProjectsTime = measureTimeMillis {
            createNativeProjects(context, projectsCount)
        }

        val cppFetchProjectsTime = measureTimeMillis {
            val projects = fetchNativeProjects()
        }

        builder.append(if (isDebug) "\nRunning Debug" else "\nRunning Release").append("\n\n")

        builder.append("Test projects count: $projectsCount\n\n")

        builder.append("SQLDelight create projects: ${nativeCreateProjectsTime / 1000.0}\n")
        builder.append("SQLDelight fetch projects: ${nativeFetchProjectsTime / 1000.0}\n\n")

        builder.append("sqlite3 (SQLCipher) create projects: ${cppCreateProjectsTime / 1000.0}\n")
        builder.append("sqlite3 (SQLCipher) fetch projects: ${cppFetchProjectsTime / 1000.0}")

        Napier.d("$builder")

        return builder.toString()
    }

}