plugins {
    application
    java
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}

repositories { mavenCentral() }

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test { useJUnitPlatform() }

application {
    mainClass.set("demo.DemoLauncher")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
