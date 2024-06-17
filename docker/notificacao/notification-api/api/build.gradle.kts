import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    // id("org.graalvm.buildtools.native") version "0.9.27"
}

repositories {
    mavenCentral()
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


dependencyLocking {
    lockAllConfigurations()
}


dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.20.95"))
    implementation("software.amazon.awssdk:ses")

    implementation("net.logstash.logback:logstash-logback-encoder:7.4")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.flywaydb:flyway-core")

    implementation("org.springframework.boot:spring-boot-starter-amqp")
    testImplementation("org.springframework.amqp:spring-rabbit-test")

    runtimeOnly("org.postgresql:postgresql")

    testRuntimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    annotationProcessor("org.springframework:spring-context-indexer")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<BootBuildImage>("bootBuildImage") {
    docker {
        imageName.set("registry.gitlab.com/pdsa-rs/notification-api")

        publishRegistry {
            username.set(System.getenv("CI_REGISTRY_USER"))
            password.set(System.getenv("CI_REGISTRY_PASSWORD"))
        }
    }
}

group = "br.com.pdsa-rs.notification-api"
description = "api"
java.sourceCompatibility = JavaVersion.VERSION_17
