plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

apply(from = "../maven-publish-helper.gradle")

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.compileSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "shared-compose-exoplayer"
            pom {
                name.set("Shared Compose Exoplayer Library")
                description.set("Shared Compose Exoplayer Library")
            }
        }
        create<MavenPublication>("debug") {
            artifactId = "shared-compose-exoplayer"
            pom {
                name.set("Shared Compose Exoplayer Library")
                description.set("Shared Compose Exoplayer Library")
            }
        }
    }
}

dependencies {
    implementation(libs.bundles.core)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.commonMark)

    implementation(libs.coil)

    testImplementation(testing.bundles.core)
    androidTestImplementation(instrumentation.bundles.core)
}