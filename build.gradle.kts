import com.google.protobuf.gradle.*
import java.net.URI

plugins {
    id("java")
    id("com.google.protobuf") version("0.9.4")
}

group = "org.dlms"
version = "1.0-SNAPSHOT"
val grpcVersion = "1.68.0"

repositories {
    mavenCentral()
    google()
    maven {
        url = URI.create("https://maven.pkg.github.com/bxute/dlms-schemas") // For release
        credentials {
            username = (project.findProperty("gpr.user") ?: "").toString()
            password = (project.findProperty("gpr.token") ?: "").toString()
        }
    }
}

dependencies {
    implementation("io.grpc:grpc-all:${grpcVersion}")
    implementation("com.google.protobuf:protobuf-java:4.28.2")

    // annotation
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    // schemas
    implementation("org.dlms.protos:dlms-schemas:1.0.4")

    // tests
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:3.25.3" }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
    }
    generateProtoTasks {
        all().forEach { tasks ->
            tasks.plugins {
                id("grpc") {}
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("$buildDir/extracted-include-protos/main")
        }
        java {
            srcDir("$projectDir/src/generated/")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks {
    compileJava {
        dependsOn("generateProto")
    }
    processResources {
        dependsOn("extractIncludeProto")
    }
}

tasks.test {
    useJUnitPlatform()
}