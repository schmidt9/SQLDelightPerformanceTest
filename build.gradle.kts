buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.android.tools.build:gradle:7.4.2")
    }
}

plugins {
    id("app.cash.sqldelight") version "2.0.2" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}