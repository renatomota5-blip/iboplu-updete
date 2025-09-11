// build.gradle.kts (root) — Kotlin DSL

plugins {
    // vazio na raiz; plugins ficam no módulo :app
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}
