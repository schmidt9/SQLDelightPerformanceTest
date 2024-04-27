package com.example.sqldelightperformancetest.shared

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.RunningTest(false)) {

    sealed class State {
        data class RunningTest(val isRunning: Boolean) : State()
        data class Result(val records: List<Any>) : State()
    }

    fun runRecordsTest() {
        screenModelScope.launch(context = Dispatchers.IO) {
            mutableState.value = State.RunningTest(isRunning = true)
            delay(2000)
            val records = listOf<Any>() // TODO: impl
            mutableState.value = State.RunningTest(isRunning = false)
            mutableState.value = State.Result(records)
        }
    }

}