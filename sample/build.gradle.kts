import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":composePPT"))

    // Add the compose compiler to kotlin compiler classpath
    add(PLUGIN_CLASSPATH_CONFIGURATION_NAME, "org.jetbrains.compose.compiler:compiler:1.4.5")
}