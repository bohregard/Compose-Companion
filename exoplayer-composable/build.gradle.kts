plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

val PUBLISH_GROUP_ID by extra { "com.bohregard" }
val PUBLISH_VERSION by extra { Versions.library }
val PUBLISH_ARTIFACT_ID by extra { "exoplayer-composable" }
val PUBLISH_NAME by extra { "Compose Exoplayer View" }
val PUBLISH_DESCRIPTION by extra { "Compose Exoplayer View" }

apply(from = "../maven-publish-helper.gradle")

android {
    compileSdk = Versions.compileSdk
    namespace = "com.bohregard.exoplayercomposable"

    defaultConfig {
        minSdk = Versions.minSdk
        testOptions.targetSdk = Versions.compileSdk

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
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {

    implementation(project(":shared"))
    implementation(project(":shared-compose"))
    implementation(libs.bundles.core)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    // ExoPlayer
    implementation(libs.exoplayer)

    testImplementation(libs.junit)
}