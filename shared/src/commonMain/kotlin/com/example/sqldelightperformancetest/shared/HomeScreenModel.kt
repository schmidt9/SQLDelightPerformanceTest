package com.example.sqldelightperformancetest.shared

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
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

    private val eventBus = EventBus<State>()

    sealed class State {
        data class RunningTest(val isRunning: Boolean) : State()
        data class Result(val records: List<Any>) : State()
    }

    fun runRecordsTest() {
        screenModelScope.launch(context = Dispatchers.IO) {
            eventBus.subscribe {
                mutableState.value = it
            }

            eventBus.emit(State.RunningTest(isRunning = true))
            delay(1000) // testing
            val records = listOf<Any>() // TODO: impl
            eventBus.emit(State.RunningTest(isRunning = false))
            eventBus.emit(State.Result(records))
        }
    }

}

/**
 * This class allows to wait for all subscribers to collect values avoiding conflation
 *
 * https://github.com/Kotlin/kotlinx.coroutines/issues/2603#issuecomment-1480598729
 *
 * about conflation: https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/
 */
@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class EventBus<T> {
    private val context = newSingleThreadContext("EventBus")
    private val scope = CoroutineScope(context)
    private val _events = MutableSharedFlow<T>()

    suspend fun emit(event: T) = withContext(context) {
        _events.emit(event)
    }

    fun subscribe(block: (event: T) -> Unit) = _events
        .onEach { block(it) }
        .launchIn(scope)
}