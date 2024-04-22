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

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = fetchTestResults()
    }

    private fun fetchTestResults(): String {
        val databaseTest = DatabaseTest(this)
        val builder = StringBuilder()

        val createProjectsTime = measureTimeMillis {
            databaseTest.createProjects()
        }

        val fetchProjectsTime = measureTimeMillis {
            databaseTest.fetchProjects()
        }

        builder.append("createProjectsTime: ${createProjectsTime / 1000.0}\n")
        builder.append("fetchProjectsTime: ${fetchProjectsTime / 1000.0}")

        Log.d("Tests", "$builder")

        return builder.toString()
    }
}
