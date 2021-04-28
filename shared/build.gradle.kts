plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("org.jetbrains.kotlin.native.cocoapods")
}

kotlin {
    android()

    // Revert to just ios() when gradle plugin can properly resolve it
    // https://github.com/cashapp/sqldelight/issues/2044
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:runtime:1.5.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
                implementation("com.squareup.sqldelight:android-driver:1.5.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:1.5.0")
            }
        }
        val iosTest by getting
    }

    sqldelight {
        database("TestDatabase") {
            packageName = "com.example.db"
        }
        linkSqlite = false
    }

    // CocoaPods requires the podspec to have a version.
    version = "1.0.0"

    cocoapods {
        // Configure fields required by CocoaPods.
        val projectName = project.getRootProject().getName()

        license = "MIT"
        summary = projectName
        homepage = "https://google.com"
        ios.deploymentTarget = "9.0"

        frameworkName = projectName
    }
}

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
    }
}