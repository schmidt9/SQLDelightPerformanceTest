package com.example.sqldelightperformancetest.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { HomeScreenModel() }
        val screenModelState by screenModel.state.collectAsState()
        var isRunningTest by remember { mutableStateOf(false) }
        var logString by remember { mutableStateOf("") }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(true) {
                when (val state = screenModelState) {
                    is HomeScreenModel.State.RunningTest -> {
                        isRunningTest = state.isRunning
                        logString = ""
                    }
                    is HomeScreenModel.State.Result -> {
                        isRunningTest = false
                        logString = state.logString
                    }
                }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = {
                        screenModel.runRecordsTest()
                    }, enabled = !isRunningTest) {
                        Text("Run records test")
                    }

                    if (logString.isNotBlank()) {
                        Text(logString)
                    }

                    if (isRunningTest) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            CircularProgressIndicator()
                            Text("Running test")
                        }
                    }

                }
            }
        }
    }

}