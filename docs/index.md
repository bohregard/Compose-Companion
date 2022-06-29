# Compose Companion
[![Github Release](https://badgen.net/github/release/bohregard/Shared-Library)](https://GitHub.com/bohregard/Shared-Library/releases/)
[![Latest Tag](https://badgen.net/github/tag/bohregard/Shared-Library)](https://GitHub.com/bohregard/Shared-Library/tags/)

Root `build.gradle.kts` repository configuration. *Note*: You'll need to create a [github token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) to access GitHub Packages.

=== "Kotlin"
    
    ```kotlin
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/bohregard/Shared-Library")
        credentials {
            username = githubUser
            password = githubToken
        }
    }
    ```