plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
    id("systems.manifold.manifold-gradle-plugin") version "0.0.2-alpha"
}

group = "io.github.rysefoxx.plugin"
paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
val inventoryVersion = "1.6.5.26-DEV"

manifold {
    manifoldVersion.set("2025.1.9")
}


repositories {
    maven("https://s01.oss.sonatype.org/content/groups/public/")
}

dependencies {
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")

    compileOnly(project(":infrastructure"))
    compileOnly("io.github.rysefoxx.inventory:RyseInventory-Plugin:$inventoryVersion")

    implementation("systems.manifold:manifold-json-rt:${manifold.manifoldVersion.get()}")
    implementation("systems.manifold:manifold-props-rt:${manifold.manifoldVersion.get()}")

    annotationProcessor("systems.manifold:manifold-json:${manifold.manifoldVersion.get()}")
    annotationProcessor("systems.manifold:manifold-props:${manifold.manifoldVersion.get()}")

    testImplementation("systems.manifold:manifold-json-rt:${manifold.manifoldVersion.get()}")
    testImplementation("systems.manifold:manifold-props-rt:${manifold.manifoldVersion.get()}")

    testAnnotationProcessor("systems.manifold:manifold-json:${manifold.manifoldVersion.get()}")
    testAnnotationProcessor("systems.manifold:manifold-props:${manifold.manifoldVersion.get()}")
}