plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

apply {
    from("./scripts/publish-root.gradle")
}