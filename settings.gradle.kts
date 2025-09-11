pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "iboplu-updete"

// inclui o módulo e força o diretório do projeto (evita o Gradle ignorar)
include(":app")
project(":app").projectDir = file("app")
