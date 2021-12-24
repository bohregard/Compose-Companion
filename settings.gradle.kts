rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "Bohregard Shared Library"
include(":app",":shared",":shared-compose",":shared-compose-explayer",":shared-di",":shared-compose-markdown")
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.5.31")
            version("compose-version", "1.1.0-beta03")
            version("material3", "1.0.0-alpha02")

            alias("stdlib").to("org.jetbrains.kotlin", "kotlin-stdlib").versionRef("kotlin")
            alias("appCompat").to("androidx.appcompat:appcompat:1.3.1")
            alias("core").to("androidx.core:core-ktx:1.6.0")
            alias("material").to("com.google.android.material:material:1.4.0")

            bundle("core", listOf("stdlib", "appCompat", "core", "material"))

            alias("compose-ui").to("androidx.compose.ui", "ui").versionRef("compose-version")
            alias("compose-ui-tooling").to("androidx.compose.ui", "ui-tooling-preview").versionRef("compose-version")
            alias("compose-material").to("androidx.compose.material", "material").versionRef("compose-version")
            alias("compose-material3").to("androidx.compose.material3", "material3").versionRef("material3")
            alias("compose-lifecycle").to("androidx.compose.runtime", "runtime-livedata").versionRef("compose-version")
            alias("compose-viewmodel").to("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")

            bundle("compose", listOf("compose-ui", "compose-ui-tooling", "compose-material", "compose-material3", "compose-lifecycle", "compose-viewmodel"))

            alias("coroutines").to("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
            alias("coroutinesAndroid").to("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

            bundle("coroutines", listOf("coroutines", "coroutinesAndroid"))

            alias("commonMark").to("org.commonmark:commonmark:0.17.1")
            alias("commonMarkTable").to("org.commonmark:commonmark-ext-gfm-tables:0.17.1")

            bundle("commonMark", listOf("commonMark", "commonMarkTable"))
        }
    }
}