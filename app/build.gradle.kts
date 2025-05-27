plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.chefschoice"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.chefschoice"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "SPOONACULAR_API_KEY", "\"575ab1371d4d48a596b7c608bc35cdbc\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core dependencies
    implementation(libs.appcompat)
    implementation(libs.material) // or use implementation("com.google.android.material:material:1.11.0")
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.core.ktx.v1120) // Updated from 1.7.0

    // Firebase
    implementation(libs.firebase.auth.ktx)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)

    // Navigation (consolidated)
    implementation(libs.navigation.fragment.ktx) // Replaces navigation.fragment
    implementation(libs.navigation.ui.ktx)

    // Network
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi.kotlin) // Updated

    // Image loading
    implementation(libs.coil) // Updated from 1.4.0
    implementation(libs.glide)
    implementation(libs.play.services.vision.common)
    implementation(libs.play.services.vision) // Updated from 4.13.0
    kapt(libs.compiler)

    // Room
    // Room - using version 2.4.0 for compatibility
    val roomVersion = "2.4.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")

    // ML Kit
    implementation(libs.image.labeling)
    implementation(libs.barcode.scanning) // Updated

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Credential Manager
    implementation(libs.androidx.credentials.v120)
    // For Google Sign-In support
    implementation(libs.androidx.credentials.play.services.auth.v120)
}