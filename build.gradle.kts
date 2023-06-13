plugins {
    kotlin("jvm") version "1.8.21"
    id("io.papermc.paperweight.userdev") version "1.5.5"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

group = "kr.skymin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    paperweight.paperDevBundle("1.17.1-R0.1-SNAPSHOT")

    implementation("io.github.monun:kommand-api:3.1.3")
}