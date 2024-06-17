repositories {
    mavenCentral()
}

plugins {
    java
    `java-library`
    `maven-publish`
}

repositories {
    maven {
        url = uri("https://gitlab.com/api/v4/groups/6986954/-/packages/maven")
        name = "GitLab"
    }
}

dependencyLocking {
    lockAllConfigurations()
}

val httpClientVersion = "5.2.1"
val junitVersion = "5.8.1"

dependencies {
    implementation("com.github.spotbugs:spotbugs-annotations:4.7.3")

    implementation("org.slf4j:slf4j-api:2.0.5")

    implementation("org.apache.httpcomponents.client5:httpclient5:$httpClientVersion")
    implementation("org.apache.httpcomponents.client5:httpclient5-fluent:$httpClientVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")

    implementation("com.github.scribejava:scribejava-apis:8.3.3")

    compileOnly("org.projectlombok:lombok:1.18.26")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

group = "br.com.pdsa-rs.notificationapi"
description = "notification-api"
version = "1.1.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("library") {
            groupId = group.toString()
            artifactId = "client"
            version = project.version.toString()
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://gitlab.com/api/v4/projects/36065801/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Deploy-Token"
                value = extra["gitLabDeployToken"].toString() 
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
            }
        }
    }
}
