plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ajoberstar.grgit)
    id("jacoco")
}

fun getVersionTag():String {
    val grGit = grgitService.service.get().grgit
    if (System.getenv("VERSION_TAG").isNullOrEmpty()) {
        return android.defaultConfig.versionName + "-" + grGit.head().abbreviatedId.toString()
    }
    return System.getenv("VERSION_TAG")
}

android {
    namespace = "com.coryroy.birdfruit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.coryroy.birdfruit"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        setProperty("archivesBaseName", "birdfruit-" + getVersionTag())
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

tasks.withType<Test> {
    extensions.configure(JacocoTaskExtension::class.java) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

tasks.register("jacocoTestReport", JacocoReport::class.java) {
    dependsOn("testDebugUnitTest")


    sourceDirectories.setFrom(files("src/main/java"))
    classDirectories.setFrom(files("build/tmp/kotlin-classes/debug"))
}

tasks.register("writeVersionTag") {
    File("spinnaker.properties").printWriter().use { out ->
        out.println("versionTag=" + getVersionTag())
    }
}

tasks.register("tag") {
    val grGit = grgitService.service.get().grgit
    val tagName = getVersionTag()
    println("Adding $tagName tag")
    grGit.tag.add(mapOf(Pair("name", tagName)))
    println("Pushing $tagName tag")
    grGit.push(mapOf("tags" to true))

    /* Keeping this for testing temporarily
    val tagName = "show"
    println("Removing $tagName tag")
    grGit.tag.remove(mapOf(Pair("names", listOf(tagName))))
    println("Pushing $tagName tag")
    grGit.push(mapOf(Pair("refsOrSpecs", listOf(":refs/tags/$tagName"))))
    */
}

tasks.register("branch") {
    val grGit = grgitService.service.get().grgit
    val commitId = getVersionTag().split("-")[1]
    val branchName = "release/" + getVersionTag()
    grGit.checkout(mapOf(Pair("branch", "$branchName"),Pair("createBranch", true),Pair("startPoint", commitId)))
    grGit.push(mapOf(Pair("remote", "https://source.corp.lookout.com/cory-roy/BirdFruit.git"),Pair("refsOrSpecs", listOf(":refs/heads/$branchName"))))
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.espresso.contrib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Add Mockito dependencies
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline) // for mocking final classes
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.androidx.core.testing)
}