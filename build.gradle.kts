
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    application
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

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}
