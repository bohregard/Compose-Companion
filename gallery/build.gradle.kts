plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

apply(from = "../maven-publish-helper.gradle")

android {
    namespace = "com.bohregard.gallery"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.compileSdk

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
            artifactId = "gallery"
            pom {
                name.set("Compose Gallery View")
                description.set("Compose Gallery View")
            }
        }
        create<MavenPublication>("debug") {
            artifactId = "gallery"
            pom {
                name.set("Compose Gallery View")
                description.set("Compose Gallery View")
            }
        }
    }
}

dependencies {
    implementation(libs.bundles.core)
    implementation(libs.bundles.compose)
    implementation(libs.coil)
    implementation(accompanist.bundles.pager)

    testImplementation(testLibraries.bundles.core)
    androidTestImplementation(instrumentation.bundles.core)
    debugImplementation(libs.bundles.compose.debug)

}