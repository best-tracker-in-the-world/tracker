plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.rogzy"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val jjwtVersion by extra { "0.12.6" }

dependencies {
	//spring boot
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	//migration
	implementation("org.liquibase:liquibase-core")
	//mysql
	runtimeOnly("com.mysql:mysql-connector-j")
	//testing
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	//jwt
	implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
	implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
	implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
	//security
	implementation("org.mindrot:jbcrypt:0.4")
	//openapi
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
	//simplifier
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.mapstruct:mapstruct:1.6.3")
	//h2
	runtimeOnly("com.h2database:h2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
