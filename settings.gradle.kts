pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "CactusClicker"
include("core")
include("application")
include("infrastructure")
include("plugin")
