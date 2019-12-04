import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":please-review-common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
