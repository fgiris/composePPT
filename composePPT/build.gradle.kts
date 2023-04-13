import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
    id("kotlin")
    id("com.vanniktech.maven.publish")
}

dependencies {
    api("org.jetbrains.compose.runtime:runtime:1.4.0")

    implementation("org.apache.poi:poi:5.2.2")
    implementation("org.apache.poi:poi-ooxml:5.2.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    // Add the compose compiler to kotlin compiler classpath
    add(PLUGIN_CLASSPATH_CONFIGURATION_NAME, "org.jetbrains.compose.compiler:compiler:1.4.5")
}

tasks.test {
    useJUnitPlatform()
}

allprojects {
    group = "com.fatihgiris.composePPT"
    version = "0.1.0"

    plugins.withId("com.vanniktech.maven.publish") {
        mavenPublish {
            sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
        }
    }
}