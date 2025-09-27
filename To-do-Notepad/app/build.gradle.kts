plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android") version "1.9.0" apply true // ✅ Kotlin plugin
    id("kotlin-kapt") // ✅ Required for Room Database
}

android {
    namespace = "com.example.to_do_notepad"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.to_do_notepad"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // 🔹 Upgraded to Java 17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true // 🔹 Enables Java 8+ features on older devices
    }

    buildFeatures {
        viewBinding = true // ✅ ViewBinding enabled for easier UI handling
    }

    kotlinOptions {
        jvmTarget = "17" // 🔹 Ensure Kotlin compatibility with Java 17
    }

    // Add JVM arguments to allow access to JavaCompiler class
    tasks.withType<JavaCompile> {
        options.isFork = true
        // Ensure that jvmArgs is non-null and mutable before appending the argument
        if (options.forkOptions.jvmArgs == null) {
            options.forkOptions.jvmArgs = mutableListOf() // Initialize as a new mutable list
        }
        options.forkOptions.jvmArgs!!.addAll(listOf("--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED"))
    }


    dependencies {
        // ✅ Core AndroidX dependencies
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.activity:activity-ktx:1.8.2")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")

        // ✅ AndroidX Annotations
        implementation("androidx.annotation:annotation:1.7.1")

        // ✅ Room Database (Upgraded to latest stable 2.6.0)
        implementation("androidx.room:room-runtime:2.6.0")
        kapt("androidx.room:room-compiler:2.6.0")
        implementation("androidx.room:room-ktx:2.6.0") // 🔹 Added for coroutines support

        // ✅ Core Library Desugaring (Java 8+ features support)
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

        // ✅ Testing dependencies
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }
}

