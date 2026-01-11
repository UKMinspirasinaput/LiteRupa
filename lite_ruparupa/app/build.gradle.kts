plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    // ... existing code ...

    buildFeatures {
        viewBinding = true
    }
}

android {
    namespace = "com.example.literupa"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.literupa"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Ini sudah mencakup Material Design dan ConstraintLayout versi terbaru
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material) // Ini sudah mewakili com.google.android.material
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout) // Ini sudah mewakili androidx.constraintlayout
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    // Pakai tanda kutip dua jika file kamu .kts, atau kutip satu jika .gradle biasa
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:34.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth:24.0.1")



}
