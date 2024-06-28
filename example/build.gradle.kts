plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = Versions.compileSdk
    namespace = "com.bohregard.example"

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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.activity)
    implementation(libs.exoplayer)

    testImplementation(libs.junit)

    val nav_version = "2.4.2"

    implementation("androidx.navigation:navigation-compose:$nav_version")
}
