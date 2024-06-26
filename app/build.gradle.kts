plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ajoberstar.grgit)
    id("jacoco")
}

fun getVersionTag():String {
    var versionTag = System.getenv("VERSION_TAG")
    if (System.getenv("VERSION_TAG").isNullOrEmpty()) {
        val grGit = grgitService.service.get().grgit
        versionTag = android.defaultConfig.versionName + "-" + grGit.head().abbreviatedId.toString()
        grGit.close()
    }
    return versionTag
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
    grGit.close()

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
    println("Checking out $commitId as branch: $branchName")
    grGit.checkout(mapOf(Pair("branch", "$branchName"),Pair("createBranch", true),Pair("startPoint", commitId)))
    println("Pushing branch: $branchName")
    grGit.push(mapOf(Pair("remote", "git@source.corp.lookout.com:cory-roy/BirdFruit.git"),Pair("refsOrSpecs", listOf("HEAD:refs/heads/$branchName"))))
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