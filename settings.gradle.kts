pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven() {
            url = uri("https://maven.google.com")
        }
    }
}

rootProject.name = "BirdFruit"
include(":app")
 