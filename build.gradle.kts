import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.github.rysefoxx.cactusclicker"

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<ShadowJar> {
        archiveClassifier.set("")
        archiveBaseName.set(project.name)
        archiveVersion.set(project.version.toString())
    }

    delete(rootProject.layout.buildDirectory)
    layout.buildDirectory.set(rootProject.layout.buildDirectory.dir("submodules/${project.name}"))


    dependencies {
        val jetbrainsVersion = "26.0.2"
        implementation("org.jetbrains:annotations:$jetbrainsVersion")
    }
}

allprojects {
    version = "0.1-alpha"

    repositories {
        mavenCentral()
    }

    dependencies {
        val lombokVersion = "1.18.34"

        compileOnly("org.projectlombok:lombok:$lombokVersion")
        annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {
    implementation(project(":application"))
    implementation(project(":core"))
    implementation(project(":infrastructure"))
    implementation(project(":plugin"))
}

tasks {
    compileJava {
        options.release.set(java.toolchain.languageVersion.get().asInt())
        options.encoding = "UTF-8"
    }

    runServer {
        minecraftVersion("1.21.5")
    }

    shadowJar {
        archiveClassifier.set("")
        archiveBaseName.set(project.name)
        archiveVersion.set(project.version.toString())
    }
}