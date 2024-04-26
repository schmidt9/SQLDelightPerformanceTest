package com.example.sqldelightperformancetest.shared

import cafe.adriel.voyager.core.model.StateScreenModel

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.Loading) {

    sealed class State {
        data object Loading : State()
        data class Result(val projects: List<Any>) : State()
    }

}