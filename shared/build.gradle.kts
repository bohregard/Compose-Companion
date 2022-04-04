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
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "shared"
            pom {
                name.set("Shared Library")
                description.set("Shared Library")
            }
        }
        create<MavenPublication>("debug") {
            artifactId = "shared"
            pom {
                name.set("Shared Library")
                description.set("Shared Library")
            }
        }
    }
}

dependencies {

    implementation(libs.kotlin.reflect)
    implementation(libs.bundles.core)
    api(libs.bundles.coroutines)

    implementation(square.moshi)

    testImplementation(testing.bundles.core)
    androidTestImplementation(instrumentation.bundles.core)
}
