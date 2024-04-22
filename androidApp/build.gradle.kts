plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
}

android {
    compileSdkVersion(34)
    defaultConfig {
        applicationId = "com.example.sqldelightperformancetest.androidApp"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        ndk {
            moduleName = "SQLCipherTest"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    externalNativeBuild {
        ndkBuild {
            path = File("../SQLCipherTest/Android.mk")
        }
    }
}