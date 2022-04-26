import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
    id("kotlin")
}

group = "com.fatihgiris.composePPT"
version = "0.1.0"

dependencies {
    api("org.jetbrains.compose.runtime:runtime:1.1.1")

    // Add the compose compiler to kotlin compiler classpath
    add(PLUGIN_CLASSPATH_CONFIGURATION_NAME, "org.jetbrains.compose.compiler:compiler:1.1.1")
}
