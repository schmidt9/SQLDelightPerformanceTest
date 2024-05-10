package com.example.sqldelightperformancetest.shared

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.db.DatabaseTest
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import sqldelightperformancetest.shared.generated.resources.Res
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

    enum class ImageTestMode {
        SQLDELIGHT,
        SQLITE3,
        ALL
    }

    // region Simple text test

    // TODO: add encryption to tests like in https://github.com/softartdev/NoteDelight

    fun runTests(mode: TestMode) {
        val projectsCount = 100_000

        screenModelScope.launch(Dispatchers.IO) {
            val builder = StringBuilder()
            builder.append(if (isDebug) "\nRunning Debug" else "\nRunning Release").append("\n\n")
            builder.append("Type: simple text projects\n\n")
            builder.append("Test projects count: $projectsCount\n\n")

            mutableState.value = State.RunningTest(isRunning = true)

            val logString: String = when (mode) {
                TestMode.SQLDELIGHT -> runSqlDelightTest(projectsCount)
                TestMode.SQLITE3 -> runSqlite3Test(projectsCount)
                TestMode.ALL -> runSqlDelightTest(projectsCount) + runSqlite3Test(projectsCount)
            }

            builder.append(logString)

            Napier.d("$builder")

            mutableState.value = State.Result(builder.toString())
        }
    }

    private fun runSqlDelightTest(projectsCount: Int): String {
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

    private fun runSqlite3Test(projectsCount: Int): String {
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

    // endregion

    // region Image test

    @OptIn(ExperimentalResourceApi::class)
    fun runImageTests(mode: ImageTestMode) {
        val projectsCount = 100

        screenModelScope.launch(Dispatchers.IO) {
            val builder = StringBuilder()
            builder.append(if (isDebug) "\nRunning Debug" else "\nRunning Release").append("\n\n")
            builder.append("Type: image projects\n\n")
            builder.append("Test projects count: $projectsCount\n\n")

            mutableState.value = State.RunningTest(isRunning = true)

            val imageData = Res.readBytes("drawable/cat.jpeg")

            val logString: String = when (mode) {
                ImageTestMode.SQLDELIGHT -> runSqlDelightImageTest(projectsCount, imageData)
                ImageTestMode.SQLITE3 -> runSqlite3ImageTest(projectsCount, imageData)
                ImageTestMode.ALL -> runSqlDelightImageTest(projectsCount, imageData) + runSqlite3ImageTest(projectsCount, imageData)
            }

            builder.append(logString)

            Napier.d("$builder")

            mutableState.value = State.Result(builder.toString())
        }
    }

    private fun runSqlDelightImageTest(projectsCount: Int, imageData: ByteArray): String {
        val databaseTest = DatabaseTest()
        val builder = StringBuilder()

        val sqlDelightCreateProjectsTime = measureTimeMillis {
            databaseTest.createImageProjects(projectsCount, imageData)
        }

        val sqlDelightFetchProjectsTime = measureTimeMillis {
            databaseTest.fetchProjects()
        }

        builder.append("SQLDelight create projects: ${sqlDelightCreateProjectsTime / 1000.0}\n")
        builder.append("SQLDelight fetch projects: ${sqlDelightFetchProjectsTime / 1000.0}\n\n")

        return builder.toString()
    }

    private fun runSqlite3ImageTest(projectsCount: Int, imageData: ByteArray): String {
        val builder = StringBuilder()

        val sqlite3CreateProjectsTime = measureTimeMillis {
            createNativeImageProjects(projectsCount, imageData)
        }

        val sqlite3FetchProjectsTime = measureTimeMillis {
            fetchNativeProjects()
        }

        builder.append("sqlite3 (SQLCipher) create projects: ${sqlite3CreateProjectsTime / 1000.0}\n")
        builder.append("sqlite3 (SQLCipher) fetch projects: ${sqlite3FetchProjectsTime / 1000.0}")

        return builder.toString()
    }

    // endregion

}