plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.bohregard.example"
        minSdk = Versions.minSdk
        targetSdk = Versions.compileSdk
        versionCode = 1
        versionName = "1.0-${Versions.library}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "LIBRARY_VERSION", "\"${Versions.library}\"")
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

    implementation(project(":shared"))
    implementation(project(":shared-compose"))
    implementation(project(":markdown"))
    implementation(project(":datetime-picker"))
    implementation(project(":exoplayer-composable"))
    implementation(project(":animated-textfield"))
    implementation(project(":gallery"))
    implementation(libs.bundles.core)

    implementation(libs.bundles.compose)
    implementation(libs.compose.activity)
    implementation(libs.exoplayer)

    testImplementation(testLibraries.bundles.core)
    androidTestImplementation(instrumentation.bundles.core)

    val nav_version = "2.4.2"

    implementation("androidx.navigation:navigation-compose:$nav_version")
}
