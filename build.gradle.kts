plugins {
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {

    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // H2 Database
    runtimeOnly("com.h2database:h2")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // JWT (Para generar y validar tokens JWT)
    implementation("io.jsonwebtoken:jjwt:0.12.6")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    //Pruebas unitarias
    testImplementation("org.mockito:mockito-core")

    //Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")


}

tasks.test {
    useJUnitPlatform()
}