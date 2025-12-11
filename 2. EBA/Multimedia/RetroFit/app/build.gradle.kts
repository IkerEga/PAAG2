plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.retrofit"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.retrofit"
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation (libs.retrofit)
    implementation (libs.retrofit2.converter.gson)
    implementation (libs.glide)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    annotationProcessor (libs.compiler)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}