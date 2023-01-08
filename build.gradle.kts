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
@file:Suppress("UnstableApiUsage", "SuspiciousCollectionReassignment")

import com.gw2tb.apigen.build.*
import com.gw2tb.apigen.build.BuildType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.*
import java.net.URL

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.gradle.toolchain.switches)
    alias(libs.plugins.dokka)
    alias(libs.plugins.binary.compatibility.validator)
    signing
    `maven-publish`
}

val artifactName = "api-generator"
val nextVersion = "0.7.0"

group = "com.gw2tb.api-generator"
version = when (deployment.type) {
    BuildType.SNAPSHOT -> "$nextVersion-SNAPSHOT"
    else -> nextVersion
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }

    withJavadocJar()
    withSourcesJar()
}

kotlin {
    explicitApi()

    target {
        compilations.all {
            compilerOptions.configure {
                apiVersion.set(KotlinVersion.KOTLIN_1_8)
                languageVersion.set(KotlinVersion.KOTLIN_1_8)

                freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            }
        }
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
        dependsOn(dokkaJavadoc)
        from(dokkaJavadoc.get().outputs)
    }

    dokkaHtml {
        outputDirectory.set(layout.buildDirectory.dir("mkdocs/sources/api").map { it.asFile })
        failOnWarning.set(true)

        dokkaSourceSets.configureEach {
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
            jdkVersion.set(8)

            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/GW2ToolBelt/api-generator/tree/v${version}/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
        }
    }

    create("mkdocs") {
        dependsOn(dokkaHtml)

        val inputDir = layout.projectDirectory.dir("docs/mkdocs")
        inputs.dir(inputDir)
        inputs.file(layout.projectDirectory.file("mkdocs.yml"))

        val changelogFile = layout.projectDirectory.file("docs/changelog/full.md")
        inputs.file(changelogFile)

        val contributingFile = layout.projectDirectory.file(".github/CONTRIBUTING.md")
        inputs.file(contributingFile)

        val workDir = layout.buildDirectory.dir("mkdocs")

        val outputDir = workDir.map { it.dir("site") }
        outputs.dir(outputDir)

        doLast {
            delete(fileTree(workDir) {
                exclude("sources/api")
            })

            copy {
                from(inputDir)
                into(workDir.map { it.dir("sources") }.get())
                filter { it.replace("docs/mkdocs/([a-zA-Z-]*).md".toRegex(), "$1") }
            }

            copy {
                from(changelogFile)
                into(workDir.map { it.dir("sources") }.get())
                rename("full.md", "changelog.md")
            }

            copy {
                from(contributingFile)
                into(workDir.map { it.dir("sources") }.get())
                rename("CONTRIBUTING.md", "contributing.md")
            }

            exec {
                executable = "mkdocs"
                args("build", "-d", outputDir.get())
            }
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri(deployment.repo)

            credentials {
                username = deployment.user
                password = deployment.password
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = artifactName

            pom {
                name.set(project.name)
                description.set("A library for generating programs that interface with the official Guild Wars 2 API.")
                packaging = "jar"
                url.set("https://github.com/GW2ToolBelt/api-generator")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://github.com/GW2ToolBelt/api-generator/blob/master/LICENSE")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("TheMrMilchmann")
                        name.set("Leon Linhart")
                        email.set("themrmilchmann@gmail.com")
                        url.set("https://github.com/TheMrMilchmann")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/GW2ToolBelt/api-generator.git")
                    developerConnection.set("scm:git:git://github.com/GW2ToolBelt/api-generator.git")
                    url.set("https://github.com/GW2ToolBelt/api-generator.git")
                }
            }
        }
    }
}

signing {
    isRequired = (deployment.type === BuildType.RELEASE)
    sign(publishing.publications)
}

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib"))
    api(libs.kotlinx.serialization.json)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}