plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.2"
    id("org.jetbrains.kotlin.native.cocoapods")
}

kotlin {
    androidTarget()

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
                implementation(libs.runtime)
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
                implementation(libs.androidx.material)
                implementation(libs.android.driver)
                implementation(libs.junit.v413)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit.v413)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.native.driver)
            }
        }
        val iosTest by getting
    }

    sqldelight {
        databases {
            create("TestDatabase") {
                packageName.set("com.example.db")
            }
        }

        linkSqlite.set(false)
    }

    // CocoaPods requires the podspec to have a version.
    version = "1.0.0"

    cocoapods {
        // Configure fields required by CocoaPods.
        val projectName = project.rootProject.name

        license = "MIT"
        summary = projectName
        homepage = "https://google.com"
        ios.deploymentTarget = "9.0"

        framework {
            isStatic = true
            baseName = projectName
        }
    }
}

android {
    compileSdkVersion(34)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
    }
}