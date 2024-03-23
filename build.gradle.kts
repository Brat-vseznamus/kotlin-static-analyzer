plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation("com.github.kotlinx.ast:grammar-kotlin-parser-antlr-kotlin:0.1.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

koverReport {
    filters {
        excludes {
            classes("com.baeldung.code.not.covered")
        }
    }

    verify {
        rule {
            isEnabled = true
            bound {
                minValue = 80 // Minimum coverage percentage
            }
        }
    }
}