tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
apply(plugin = "com.github.ben-manes.versions")

buildscript {
    dependencies {
        classpath ("com.android.tools.build:gradle:7.4.0-alpha02")
        classpath(kotlin("gradle-plugin", version = "1.6.20")) // Update settings.gradle.kts as well
        classpath("com.github.ben-manes:gradle-versions-plugin:0.42.0")
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
