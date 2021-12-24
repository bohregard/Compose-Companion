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
            artifactId = "shared-di"
            pom {
                name.set("Shared DI Library")
                description.set("Shared DI Library")
            }
        }
        create<MavenPublication>("debug") {
            artifactId = "shared-di"
            pom {
                name.set("Shared DI Library")
                description.set("Shared DI Library")
            }
        }
    }
}

dependencies {
    implementation(libs.bundles.core)
    implementation("org.kodein.di:kodein-di-framework-android-x:7.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}