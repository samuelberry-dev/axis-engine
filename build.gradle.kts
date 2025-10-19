group = "dev.samuelberry"
version = "0.4.0"

plugins {
    application
    java
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}

repositories {
    mavenCentral()
}

// ----- LWJGL version & natives (Kotlin DSL) -----
val lwjglVersion = "3.3.4"
val os = org.gradle.internal.os.OperatingSystem.current()
val lwjglNatives = when {
    os.isWindows -> "natives-windows"
    os.isLinux   -> "natives-linux"
    else         -> "natives-macos"
}

// ----- Dependencies -----
dependencies {
    // Keep all LWJGL modules aligned via BOM
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    // Core LWJGL + math
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.joml:joml:1.10.5")

    runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")

    // Audio + OGG decoding
    implementation("org.lwjgl:lwjgl-openal")
    implementation("org.lwjgl:lwjgl-stb")
    runtimeOnly("org.lwjgl:lwjgl-openal::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-stb::$lwjglNatives")
}

tasks.test { useJUnitPlatform() }

application {
    // Set this to your real launcher. You had this before:
    mainClass.set("demo.DemoLauncher")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
