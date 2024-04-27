package com.example.sqldelightperformancetest.shared

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.RunningTest(false)) {

    // using shared flow to avoid conflation
    // see https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/
    private val _sharedState = MutableSharedFlow<State>(replay = 3)
    val sharedState = _sharedState.asSharedFlow()

    companion object {
        val initialState = State.RunningTest(false)
    }

    sealed class State {
        data class RunningTest(val isRunning: Boolean) : State()
        data class Result(val records: List<Any>) : State()
    }

    fun runRecordsTest() {
        screenModelScope.launch() {
            _sharedState.emit(State.RunningTest(isRunning = true))
            delay(1000) // testing
            val records = listOf<Any>() // TODO: impl
            _sharedState.emit(State.RunningTest(isRunning = false))
            _sharedState.emit(State.Result(records))
        }
    }

}