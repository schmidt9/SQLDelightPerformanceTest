package com.example.sqldelightperformancetest.shared

import android.app.Application
import android.content.Context
import androidx.startup.Initializer

object AppContext {
    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    fun get(): Context {
        if (::application.isInitialized.not()) throw Exception("Application context isn't initialized")
        return application.applicationContext
    }
}

/**
 * https://stackoverflow.com/questions/66269937/get-applicationcontext-from-a-kmm-module
 * https://developer.android.com/topic/libraries/app-startup
 */
class AppContextInitializer : Initializer<Context> {

    override fun create(context: Context): Context {
        AppContext.setUp(context.applicationContext)
        return AppContext.get()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}