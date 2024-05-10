package com.example.sqldelightperformancetest.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { HomeScreenModel() }
        val screenModelState by screenModel.state.collectAsState()
        var isImageTest by remember { mutableStateOf(false) }
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

                Column(Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    TitledRadioButton(
                        title = "Run simple text tests",
                        selected = isImageTest.not()) {
                        isImageTest = false
                    }

                    TitledRadioButton(title = "Run image tests", selected = isImageTest) {
                        isImageTest = true
                    }

                    Button(onClick = {
                        if (isImageTest) {
                            screenModel.runImageTests(mode = HomeScreenModel.ImageTestMode.SQLDELIGHT)
                        } else {
                            screenModel.runTests(mode = HomeScreenModel.TestMode.SQLDELIGHT)
                        }
                    }, enabled = !isRunningTest) {
                        val text = if (isImageTest) "SQLDelight (not working on Android < 9 due to full CursorWindow!)" else "SQLDelight"
                        Text(text)
                    }

                    Button(onClick = {
                        if (isImageTest) {
                            screenModel.runImageTests(mode = HomeScreenModel.ImageTestMode.SQLITE3)
                        } else {
                            screenModel.runTests(mode = HomeScreenModel.TestMode.SQLITE3)
                        }
                    }, enabled = !isRunningTest) {
                        Text("sqlite3 (SQLCipher)")
                    }

                    Button(onClick = {
                        if (isImageTest) {
                            screenModel.runImageTests(mode = HomeScreenModel.ImageTestMode.ALL)
                        } else {
                            screenModel.runTests(mode = HomeScreenModel.TestMode.ALL)
                        }
                    }, enabled = !isRunningTest) {
                        val text = if (isImageTest) "SQLDelight (not working on Android < 9 due to full CursorWindow!) + sqlite3 (SQLCipher)" else "SQLDelight + sqlite3 (SQLCipher)"
                        Text(text)
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

@Composable
fun TitledRadioButton(title: String, selected: Boolean, onClick: (Boolean) -> Unit) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selected,
                onClick = { onClick(selected.not()) }
            )
        }
    }
}