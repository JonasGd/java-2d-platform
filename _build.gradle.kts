plugins {
    id("java")
}

version = "1.0-SNAPSHOT"

repositories {
    flatDir {
        dirs("libs")
    }
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

val lwjglVersion = "3.2.3"
val jomlVersion = "1.10.5"
val lwjglNatives = "natives-windows"
val imguiVersion = "1.86.10"

dependencies {
    //Box2D
    implementation(name, "jbox2d-library")

    //jUnit
    testImplementation("junit", "junit", "4.13")

    //Include all available natives, but it's likely that you want something specific
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    // LWJGL stuff
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-assimp")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-nfd")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")

    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)

    implementation("org.joml", "joml", jomlVersion)


    //ImGUI stuff
    implementation("io.github.spair", "imgui-java-app", imguiVersion)
    implementation("io.github.spair", "imgui-java-lwjgl3", imguiVersion)
    implementation("io.github.spair", "imgui-java-natives-windows", imguiVersion)

    //GSON
    implementation("com.google.code.gson", "gson", "2.8.6")

    //lombok
    compileOnly("org.projectlombok", "lombok", "1.18.26")
    annotationProcessor("org.projectlombok", "lombok", "1.18.26")
    testCompileOnly("org.projectlombok", "lombok", "1.18.26")
    testAnnotationProcessor("org.projectlombok", "lombok", "1.18.26")
}