# Compose Components
[![Github Release](https://badgen.net/github/release/bohregard/Shared-Library)](https://GitHub.com/bohregard/Shared-Library/releases/)
[![Latest Tag](https://badgen.net/github/tag/bohregard/Shared-Library)](https://GitHub.com/bohregard/Shared-Library/tags/)

## Using the Libraries

### From GitHub Packages

Root `build.gradle.kts` repository configuration. *Note*: You'll need to create a [github token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) to access GitHub Packages.

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

### From Maven Central

For release versions (when available):
```kotlin
repositories {
    mavenCentral()
}
```

For SNAPSHOT versions:
```kotlin
repositories {
    mavenCentral()
    maven { 
        url = uri("https://central.sonatype.com/repository/maven-snapshots/") 
    }
}
```

## Publishing to Maven Central

This project is configured to publish to Maven Central via Sonatype's Central Portal. Follow these steps to publish:

### Prerequisites

1. Create an account at [central.sonatype.com](https://central.sonatype.com)
2. Generate a user token from your account settings
3. Add the following to your `~/.gradle/gradle.properties`:

```properties
centralPortalUsername=your-username
centralPortalToken=your-token
```

### Publishing SNAPSHOT Versions

SNAPSHOT versions are automatically published to the Central Portal snapshots repository:

```bash
./gradlew publishReleasePublicationToCentralPortalRepository
```

SNAPSHOT artifacts will be available immediately at:
- Repository URL: `https://central.sonatype.com/repository/maven-snapshots/`
- Direct access: `https://central.sonatype.com/repository/maven-snapshots/com/bohregard/[artifactId]/[version]/`

Note: SNAPSHOT versions are retained for 90 days and can be overwritten.

### Publishing Release Versions

1. Update the version in `buildSrc/src/main/java/Dependencies.kt` to remove `-SNAPSHOT`
2. Run the publish command:
   ```bash
   ./gradlew publishReleasePublicationToCentralPortalRepository
   ```
3. The release will be uploaded to Central Portal for validation
4. Visit [central.sonatype.com/publishing](https://central.sonatype.com/publishing) to review and publish your deployment

### Publishing All Modules

To publish all modules at once:
```bash
./gradlew publishAllPublicationsToCentralPortalRepository
```

### Troubleshooting

- **404 Errors**: Ensure you're using the correct repository URLs (different for SNAPSHOT vs release)
- **Authentication Failed**: Verify your Central Portal credentials are correctly set in `gradle.properties`
- **Validation Errors**: Check the Central Portal UI for detailed validation failure messages
