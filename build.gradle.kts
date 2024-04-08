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
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    repositories {
        google()
        mavenCentral()
    }
}