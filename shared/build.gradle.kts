plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

val PUBLISH_GROUP_ID by extra { "com.bohregard" }
val PUBLISH_VERSION by extra { Versions.library }
val PUBLISH_ARTIFACT_ID by extra { "shared" }
val PUBLISH_NAME by extra { "Shared Library" }
val PUBLISH_DESCRIPTION by extra { "Shared Library" }

apply(from = "../maven-publish-helper.gradle")

android {
    compileSdk = Versions.compileSdk
    namespace = "com.bohregard.shared"

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
}

dependencies {

    implementation(libs.bundles.core)
    implementation(libs.bundles.android.core)
    implementation(libs.kotlin.reflect)
    implementation(libs.androidx.annotation)
    api(libs.bundles.coroutines)

    implementation(libs.moshi)

    testImplementation(libs.junit)
}
