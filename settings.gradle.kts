rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "Compose Companion"
include(
    ":example",
    ":shared",
    ":shared-compose",
    ":exoplayer-composable",
    ":datetime-picker",
    ":markdown",
    ":animated-textfield",
    ":gallery"
)

dependencyResolutionManagement {
    versionCatalogs {
        create("testLibraries") {
            library("junit", "junit:junit:4.13.2")
            bundle("core", listOf("junit"))
        }
        create("instrumentation") {
            version("compose-version", "1.2.0-rc02")
            library("junit", "androidx.test.ext:junit:1.1.4-alpha05")
            library("espresso", "androidx.test.espresso:espresso-core:3.5.0-alpha05")
            library("composeUiJunit", "androidx.compose.ui", "ui-test-junit4").versionRef("compose_version")
            library("composeUiManifest", "androidx.compose.ui", "ui-test-manifest").versionRef("compose_version")
            bundle("core", listOf("junit", "espresso"))
            bundle("compose", listOf("composeUiJunit", "composeUiManifest"))
        }
        create("square") {
            library("moshi", "com.squareup.moshi:moshi:1.13.0")
        }
        create("accompanist") {
            version("accompanist", "0.24.12-rc")
            library("horizontalPager", "com.google.accompanist", "accompanist-pager").versionRef("accompanist")
            library("horizontalIndicators", "com.google.accompanist", "accompanist-pager-indicators").versionRef("accompanist")
            bundle("pager", listOf("horizontalPager", "horizontalIndicators"))
        }
        create("libs") {
            version("kotlin", "1.6.21")
            version("compose-version", "1.2.0-rc02") // update buildSrc as well
            version("material", "1.6.0-beta01")
            version("material3", "1.0.0-alpha13")

            library("stdlib", "org.jetbrains.kotlin", "kotlin-stdlib").versionRef("kotlin")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
            library("appCompat", "androidx.appcompat:appcompat:1.6.0-alpha01")
            library("core", "androidx.core:core-ktx:1.8.0")
            library("material", "com.google.android.material:material:1.6.0-beta01")

            bundle("core", listOf("stdlib", "appCompat", "core", "material"))

            library("compose-activity", "androidx.activity:activity-compose:1.4.0")
            library("compose-ui", "androidx.compose.ui", "ui").versionRef("compose-version")
            library("compose-ui-tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose-version")
            library("compose-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("compose-version")
            library("compose-test-manifest", "androidx.compose.ui", "ui-test-manifest").versionRef("compose-version")
            library("compose-material", "androidx.compose.material", "material").versionRef("compose-version")
            library("compose-material3", "androidx.compose.material3", "material3").versionRef("material3")
            library("compose-lifecycle", "androidx.compose.runtime", "runtime-livedata").versionRef("compose-version")
            library("viewmodel", "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha05")
            library("lifecycle", "androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha05")

            library("exoplayer", "com.google.android.exoplayer:exoplayer:2.17.1")
            library("coil", "io.coil-kt:coil-compose:2.1.0")
            bundle(
                "compose",
                listOf(
                    "compose-activity",
                    "compose-ui",
                    "compose-ui-tooling",
                    "compose-ui-tooling-preview",
                    "compose-material",
                    "compose-material3",
                    "compose-lifecycle",
                    "viewmodel",
                    "lifecycle"
                )
            )

            bundle(
                "compose-debug",
                listOf(
                    "compose-ui-tooling",
                    "compose-test-manifest"
                )
            )

            library("coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
            library("coroutinesAndroid", "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

            bundle("coroutines", listOf("coroutines", "coroutinesAndroid"))

            version("commonMark", "0.19.0")
            library("commonMark", "org.commonmark:commonmark:0.19.0")
            library("commonMarkAutoLink", "org.commonmark", "commonmark-ext-autolink").versionRef("commonMark")
            library("commonMarkTable", "org.commonmark", "commonmark-ext-gfm-tables").versionRef("commonMark")
            library("commonMarkStrikethrough", "org.commonmark", "commonmark-ext-gfm-strikethrough").versionRef("commonMark")

            bundle("commonMark", listOf("commonMark", "commonMarkTable", "commonMarkAutoLink", "commonMarkStrikethrough"))
        }
    }
}