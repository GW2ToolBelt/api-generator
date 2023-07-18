/*
 * Copyright (c) 2019-2023 Leon Linhart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import com.gw2tb.build.tasks.MkDocs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.dokkatoo.html)
    alias(libs.plugins.dokkatoo.javadoc)
    alias(libs.plugins.gradle.toolchain.switches)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    id("com.gw2tb.maven-publish-conventions")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }

    withJavadocJar()
    withSourcesJar()
}

kotlin {
    explicitApi()

    target {
        compilations.configureEach {
            compilerOptions.configure {
                apiVersion.set(KotlinVersion.KOTLIN_1_8)
                languageVersion.set(KotlinVersion.KOTLIN_1_8)
            }
        }
    }
}

dokkatoo {
    dokkatooSourceSets.configureEach {
        reportUndocumented.set(true)
        skipEmptyPackages.set(true)
        jdkVersion.set(8)

        val localKotlinSourceDir = layout.projectDirectory.dir("src/main/kotlin")
        val version = project.version

        sourceLink {
            localDirectory.set(localKotlinSourceDir)
            remoteUrl("https://github.com/GW2ToolBelt/api-generator/tree/v${version}/src/main/kotlin")
            remoteLineSuffix.set("#L")
        }
    }

    dokkatooPublications.configureEach {
        failOnWarning.set(true)
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release.set(8)
    }

    withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    withType<Test>().configureEach {
        useJUnitPlatform()

        testLogging {
            showStandardStreams = true
        }
    }

    named<Jar>("javadocJar").configure {
        from(dokkatooGenerateModuleJavadoc.get().outputs)
    }

    dokkatooGenerateModuleHtml.configure {
        outputDirectory.set(layout.buildDirectory.dir("mkdocs/sources/api"))
    }

    register<MkDocs>("mkdocs") {
        dependsOn(dokkatooGenerateModuleHtml)

        inputFile(layout.projectDirectory.file(".github/CONTRIBUTING.md")) {
            target.set("contributing.md")
        }

        inputFile(layout.projectDirectory.file("docs/changelog/full.md")) {
            target.set("changelog.md")
        }

        inputFiles(layout.projectDirectory.dir("docs/mkdocs")) {
            this.filter { it.replace("docs/mkdocs/([a-zA-Z-]*).md".toRegex(), "$1") }
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

dependencies {
    api(kotlin("stdlib"))
    api(libs.kotlinx.serialization.json)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}