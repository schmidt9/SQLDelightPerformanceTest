plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.androidApplication)
    id("app.cash.sqldelight") version "2.0.2"
    id("org.jetbrains.kotlin.native.cocoapods")
}

kotlin {
    androidTarget()

    // Revert to just ios() when gradle plugin can properly resolve it
    // https://github.com/cashapp/sqldelight/issues/2044
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    val iosTarget = if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    // https://kotlinlang.org/docs/native-app-with-c-and-libcurl.html#before-you-start
    iosTarget.apply {
        compilations {
            getByName("main") {
                cinterops.create("TestDatabaseInterop") {
                    includeDirs {
                        allHeaders("../iosApp/iosApp")
                    }
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.runtime)

                // https://voyager.adriel.cafe/setup
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.screenmodel)

                implementation(libs.napier)
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
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.junit.v413)
                implementation(libs.android.driver)
                implementation(libs.androidx.startup.runtime)
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

    tasks.register("testClasses")
}

android {
    namespace = "com.example.sqldelightperformancetest.shared"

    compileSdkVersion(34)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        applicationId = "com.example.sqldelightperformancetest.shared"
        minSdkVersion(24)
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        ndk {
            moduleName = "SQLCipherTest"
        }
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

    dependencies {
        implementation(libs.material.v121)
    }
}