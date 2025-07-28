plugins {
    id("maven-publish")
    id("signing")
}

afterEvaluate {
    val configuration = the<PublishingConfiguration>()
    
    publishing {
        publications.create<MavenPublication>("release") {
            groupId = configuration.groupId.get()
            artifactId = configuration.artifactId.get()
            version = configuration.version.get()
            from(project.components.findByName("release"))

            pom {
                name = configuration.artifactId.get()
                description = configuration.description.get()
                url = "https://github.com/bohregard/Compose-Companion"
                scm {
                    connection = "scm:git:github.com:bohregard/Compose-Companion.git"
                    developerConnection = "scm:git:ssh://github.com:bohregard/Compose-Companion.git"
                    url = "https://github.com/bohregard/Compose-Companion"
                }

                licenses {
                    license {
                        name = "Compose Companion License"
                        url = "https://github.com/bohregard/Compose-Companion/blob/master/LICENSE"
                    }
                }
                developers {
                    developer {
                        id = "bohregard"
                        name = "Anthony Todd"
                        email = "awtodd89@gmail.com"
                    }
                }
            }
        }

        repositories {
            maven {
                name = "GitHub"
                credentials {
                    username = project.findProperty("githubUser") as String? ?: ""
                    password = project.findProperty("githubToken") as String? ?: ""
                }
                url = project.uri("https://maven.pkg.github.com/bohregard/Compose-Companion")
            }

            maven {
                name = "CentralPortal"
                val publicationVersion = configuration.version.get()
                url = if (publicationVersion.endsWith("-SNAPSHOT")) {
                    // SNAPSHOT versions use the maven-snapshots repository
                    project.uri("https://central.sonatype.com/repository/maven-snapshots/")
                } else {
                    // Release versions use the publisher upload API
                    project.uri("https://central.sonatype.com/api/v1/publisher/upload")
                }

                credentials {
                    username = project.findProperty("centralPortalUsername") as String? ?: ""
                    password = project.findProperty("centralPortalToken") as String? ?: ""
                }
            }
        }
    }
}

signing {
    sign(project.extensions.getByType(PublishingExtension::class).publications)
}

interface PublishingConfiguration {
    val groupId: Property<String>
    val version: Property<String>
    val artifactId: Property<String>
    val name: Property<String>
    val description: Property<String>
}

val extension = project.extensions.create<PublishingConfiguration>("sharedPublishing")
