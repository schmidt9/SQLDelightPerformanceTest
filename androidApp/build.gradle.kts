plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.material.v121)
    implementation(libs.androidx.appcompat.v120)
    implementation(libs.androidx.constraintlayout.v202)
}

android {
    namespace = "com.example.sqldelightperformancetest.androidApp"
    compileSdkVersion(34)
    defaultConfig {
        applicationId = "com.example.sqldelightperformancetest.androidApp"
        minSdkVersion(24)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
        ndk {
            moduleName = "SQLCipherTest"
        }
        resourceConfigurations += setOf()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    externalNativeBuild {
        ndkBuild {
            path = File("../SQLCipherTest/Android.mk")
        }
    }
}