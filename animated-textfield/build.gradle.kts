plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

val PUBLISH_GROUP_ID by extra { "com.bohregard" }
val PUBLISH_VERSION by extra { Versions.library }
val PUBLISH_ARTIFACT_ID by extra { "animated-textfield" }
val PUBLISH_NAME by extra { "Compose Animated Textfield Library" }
val PUBLISH_DESCRIPTION by extra { "Compose Animated Textfield Library" }

apply(from = "../maven-publish-helper.gradle")

android {
    namespace = "com.bohregard.animatedtextfield"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        testOptions.targetSdk = Versions.compileSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(libs.bundles.core)
    implementation(libs.androidx.annotation)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    testImplementation(libs.junit)
}