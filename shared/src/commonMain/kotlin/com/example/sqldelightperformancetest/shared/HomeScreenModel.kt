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

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.RunningTest(false)) {

    sealed class State {
        data class RunningTest(val isRunning: Boolean) : State()
        data class Result(val records: List<Any>) : State()
    }

    fun runRecordsTest() {
        screenModelScope.launch(context = Dispatchers.IO) {
            mutableState.value = State.RunningTest(isRunning = true)
            delay(1000) // for testing
            val records = listOf<Any>() // TODO: impl
            mutableState.value = State.Result(records)
        }
    }

//    private fun fetchTestResults(): String {
//        val databaseTest = DatabaseTest(this)
//        val builder = StringBuilder()
//
//        val nativeCreateProjectsTime = measureTimeMillis {
//            databaseTest.createProjects()
//        }
//
//        val nativeFetchProjectsTime = measureTimeMillis {
//            databaseTest.fetchProjects()
//        }
//
//        val cppCreateProjectsTime = measureTimeMillis {
//            CppTestDatabase.createProjects()
//        }
//
//        val cppFetchProjectsTime = measureTimeMillis {
//            val projects = CppTestDatabase.fetchProjects()
//            Log.d("Tests", "Projects count ${projects.count()}")
//        }
//
//        builder.append(if (isDebug) "\nRunning Debug" else "\nRunning Release").append("\n\n")
//
//        builder.append("nativeCreateProjectsTime: ${nativeCreateProjectsTime / 1000.0}\n")
//        builder.append("nativeFetchProjectsTime: ${nativeFetchProjectsTime / 1000.0}\n\n")
//
//        builder.append("cppCreateProjectsTime: ${cppCreateProjectsTime / 1000.0}\n")
//        builder.append("cppFetchProjectsTime: ${cppFetchProjectsTime / 1000.0}")
//
//        Log.d("Tests", "$builder")
//
//        return builder.toString()
//    }

}