plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://hub.jeff-media.com/nexus/repository/jeff-media-public/")
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

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
