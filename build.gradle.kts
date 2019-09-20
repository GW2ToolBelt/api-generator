/*
 * Copyright (c) 2019 Leon Linhart
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
import com.github.gw2toolbelt.build.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    kotlin("jvm") version "1.3.50"
    signing
    `maven-publish`
}

val artifactName = "api-generator"
val nextVersion = "0.1.0"

group = "com.github.gw2toolbelt.apigen"
version = when (deployment.type) {
    com.github.gw2toolbelt.build.BuildType.SNAPSHOT -> "$nextVersion-SNAPSHOT"
    else -> nextVersion
}

java {
    sourceCompatibility = JavaVersion.VERSION_12
    targetCompatibility = JavaVersion.VERSION_12
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "12"
    }
}

val sourcesJar = tasks.create<Jar>("sourcesJar") {
    archiveBaseName.set(artifactName)
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

val javadocJar = tasks.create<Jar>("javadocJar") {
    dependsOn(tasks.javadoc)

    archiveBaseName.set(artifactName)
    archiveClassifier.set("javadoc")
    from(tasks.javadoc.get().outputs)
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
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = artifactName

            pom {
                name.set(project.name)
                description.set("A library for generating programs that interface with the official Guild Wars 2 API.")
                packaging = "jar"
                url.set("https://github.com/GW2Toolbelt/api-generator")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://github.com/GW2Toolbelt/api-generator/blob/master/LICENSE")
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
                    connection.set("scm:git:git://github.com/GW2Toolbelt/api-generator.git")
                    developerConnection.set("scm:git:git://github.com/GW2Toolbelt/api-generator.git")
                    url.set("https://github.com/GW2Toolbelt/api-generator.git")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications)
    isRequired = deployment.type === com.github.gw2toolbelt.build.BuildType.RELEASE
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
}