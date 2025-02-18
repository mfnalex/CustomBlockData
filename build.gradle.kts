
plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.gradleup.nmcp").version("0.0.8")
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://repo.jeff-media.com/public/")
    }
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.jetbrains.annotations)

    testImplementation(libs.junit.junit)
    testImplementation(libs.slf4j)
}

group = "com.jeff-media"
version = "2.2.4"
description = "CustomBlockData"
java.sourceCompatibility = JavaVersion.VERSION_1_8

java {
    withSourcesJar()
    withJavadocJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = "com.jeff-media"
            artifactId = "custom-block-data"

            from(components["java"])

            pom {
                name.set("CustomBlockData")
                description.set("Bukkit API Library to add PersistentDataContainers to blocks")
                inceptionYear.set("2021")
                url.set("https://github.com/JEFF-Media-GbR/CustomBlockData/")

                licenses {
                    license {
                        name.set("GPL-3.0-or-later")
                        url.set("https://www.gnu.org/licenses/gpl-3.0-standalone.html")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("mfnalex")
                        name.set("Alexander Majka")
                        email.set("mfnalex@jeff-media.com")
                    }
                }

                scm {
                    url.set("https://github.com/JEFF-Media-GbR/CustomBlockData/")
                    connection.set("scm:git:git://github.com/JEFF-Media-GbR/CustomBlockData.git")
                    developerConnection.set("scm:git:ssh://git@github.com/JEFF-Media-GbR/CustomBlockData.git")
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}

nmcp {
    publishAllProjectsProbablyBreakingProjectIsolation {
        username.set(findProperty("mavenCentralUsername") as String)
        password.set(findProperty("mavenCentralPassword") as String)
        publicationType = "AUTOMATIC"
    }
}
