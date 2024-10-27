import com.google.protobuf.gradle.*
import java.net.URI

plugins {
    id("java")
    id("com.google.protobuf") version("0.8.18")
}

val grpcVersion = "1.68.0"
group = "org.dlms"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = URI.create("https://maven.pkg.github.com/bxute/dlms-schemas") // For release
        credentials {
            username = (project.findProperty("gpr.user") ?: "").toString()
            password = (project.findProperty("gpr.token") ?: "").toString()
        }
    }
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:4.28.2")
    implementation("io.grpc:grpc-all:${grpcVersion}")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.dlms.protos:dlms-schemas:1.0.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${version}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                java {}
            }
        }
    }
}

tasks.compileJava {
    dependsOn("generateProto")
}

tasks.test {
    useJUnitPlatform()
}