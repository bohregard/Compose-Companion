tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


buildscript {
    dependencies {
        classpath ("com.android.tools.build:gradle:7.2.0-alpha06")
        classpath(kotlin("gradle-plugin", version = "1.5.31"))
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
