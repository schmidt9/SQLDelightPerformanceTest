package com.example.sqldelightperformancetest.androidApp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.db.DatabaseTest
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    companion object {
        init {
            System.loadLibrary("SQLCipherTest")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.text_view).text = fetchTestResults()
    }

    private fun fetchTestResults(): String {
        val databaseTest = DatabaseTest(this)
        val builder = StringBuilder()

        val nativeCreateProjectsTime = measureTimeMillis {
            databaseTest.createProjects()
        }

        val nativeFetchProjectsTime = measureTimeMillis {
            databaseTest.fetchProjects()
        }

        val cppCreateProjectsTime = measureTimeMillis {
            CppTestDatabase.createProjects()
        }

        val cppFetchProjectsTime = measureTimeMillis {
            CppTestDatabase.fetchProjects() // TODO: convert to list of DatabaseProject
        }

        builder.append("nativeCreateProjectsTime: ${nativeCreateProjectsTime / 1000.0}\n")
        builder.append("nativeFetchProjectsTime: ${nativeFetchProjectsTime / 1000.0}\n\n")

        builder.append("cppCreateProjectsTime: ${cppCreateProjectsTime / 1000.0}\n")
        builder.append("cppFetchProjectsTime: ${cppFetchProjectsTime / 1000.0}")

        Log.d("Tests", "$builder")

        return builder.toString()
    }
}
