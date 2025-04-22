group = "io.github.rysefoxx.infrastructure"

val hypersistenceVersion = "3.9.9"
val mapStructVersion = "1.5.5.Final"
val hibernateVersion = "6.6.12.Final"

dependencies {
    api(project(":core"))
    api(project(":application"))

    api("org.springframework.boot:spring-boot-starter-data-jpa:3.4.4")
    api("org.springframework:spring-tx:6.1.5")
    api("org.springframework:spring-context-support:6.1.5")
    api("org.mariadb.jdbc:mariadb-java-client:2.7.3")
    api("org.javassist:javassist:3.29.0-GA")

    implementation("io.hypersistence:hypersistence-utils-hibernate-63:$hypersistenceVersion")
    implementation("org.mapstruct:mapstruct:$mapStructVersion")

    compileOnly("org.hibernate.orm:hibernate-core:$hibernateVersion")
    compileOnly("org.hibernate.orm:hibernate-envers:$hibernateVersion")

    annotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")
}
