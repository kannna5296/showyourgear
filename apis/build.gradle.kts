plugins {
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    kotlin("jvm") version "1.6.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    implementation("software.amazon.awssdk:s3:2.20.51")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")

    testImplementation("junit:junit:4.13.2")
    testImplementation(kotlin("test"))
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17
