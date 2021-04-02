buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath("com.android.tools.build:gradle:4.2.0-beta06")
        classpath("com.squareup.sqldelight:gradle-plugin:1.4.4")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}