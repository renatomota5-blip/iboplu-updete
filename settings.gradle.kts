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

// nome do projeto (pode ajustar se quiser mostrar outro nome no Gradle)
rootProject.name = "iboplu-updete"

// inclui o módulo app (sintaxe correta no Kotlin DSL)
include(":app")
