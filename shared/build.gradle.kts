plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

apply(from = "../maven-publish-helper.gradle")

android {
    compileSdkVersion(31)

    defaultConfig {
        minSdkVersion(26)
        targetSdkVersion(31)

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

    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
    implementation(libs.bundles.core)
    api(libs.bundles.coroutines)

    // Moshi
    implementation("com.squareup.moshi:moshi:1.12.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
