plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization") version "1.5.0"
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 19
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    implementation("io.ktor:ktor-client-android:1.6.0")
    implementation("io.ktor:ktor-client-serialization:1.6.0")

    implementation("io.insert-koin:koin-android:3.1.6")
}