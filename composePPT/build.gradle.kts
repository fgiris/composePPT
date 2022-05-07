import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
    id("kotlin")
}

group = "com.fatihgiris.composePPT"
version = "0.1.0"

dependencies {
    api("org.jetbrains.compose.runtime:runtime:1.1.1")

    implementation("org.apache.poi:poi:5.2.2")
    implementation("org.apache.poi:poi-ooxml:5.2.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    // Add the compose compiler to kotlin compiler classpath
    add(PLUGIN_CLASSPATH_CONFIGURATION_NAME, "org.jetbrains.compose.compiler:compiler:1.1.1")
}

tasks.test {
    useJUnitPlatform()
}