tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


buildscript {
    dependencies {
        classpath ("com.android.tools.build:gradle:7.2.0-alpha07")
        classpath(kotlin("gradle-plugin", version = "1.6.0"))
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
