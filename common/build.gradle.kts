import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.20-RC"
    id("org.jetbrains.compose")
    id("com.android.library")
    id("io.realm.kotlin")
}

group = "com.naipofo.archiveclient"
version = "1.0-SNAPSHOT"

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    // new ktor works only with new kotlin - that does not work with current compose
    val ktorVersion = "2.0.0-beta-1"
    val kodeinVersion = "7.11.0"
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.materialIconsExtended)
                api(compose.material3)

                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                // di
                implementation("org.kodein.di:kodein-di:$kodeinVersion")
                implementation("org.kodein.di:kodein-di-framework-compose:$kodeinVersion")

                //datetime
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.3")

                //database
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1-native-mt")
                implementation("io.realm.kotlin:library-base:0.11.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.2.0")
                api("androidx.core:core-ktx:1.3.1")

                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("org.kodein.di:kodein-di-framework-android-x:$kodeinVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)

                implementation("io.ktor:ktor-client-cio:$ktorVersion")
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}