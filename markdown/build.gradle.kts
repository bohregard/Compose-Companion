plugins {
    id("com.android.library")
    id("kotlin-android")
}

val PUBLISH_GROUP_ID by extra { "com.bohregard" }
val PUBLISH_VERSION by extra { Versions.library }
val PUBLISH_ARTIFACT_ID by extra { "markdown" }
val PUBLISH_NAME by extra { "Compose Markdown Library" }
val PUBLISH_DESCRIPTION by extra { "Compose Markdown Library" }

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

dependencies {
    implementation(libs.bundles.core)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.commonMark)

    implementation(libs.coil)

    testImplementation(testLibraries.bundles.core)
    androidTestImplementation(instrumentation.bundles.core)
    androidTestImplementation(instrumentation.bundles.compose)
}