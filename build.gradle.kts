plugins {
    `java-library`
    application
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
    withSourcesJar()
    withJavadocJar()
}

group = "dev.samuelberry.axisengine"
version = "1.3.0-SNAPSHOT"

repositories { mavenCentral() }

val lwjglVersion = "3.3.4"

dependencies {
    implementation("org.lwjgl:lwjgl:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-opengl:$lwjglVersion")

    runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw:$lwjglVersion:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-opengl:$lwjglVersion:natives-windows")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test { useJUnitPlatform() }

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
application {
    mainClass.set("engine.render.RenderHarness")
}
