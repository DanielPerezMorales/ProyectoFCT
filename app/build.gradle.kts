plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.proyectofct"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyectofct"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.config.ktx)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //Firebase
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.analytics)
    implementation(libs.play.services.auth)
    //DaggerHilt
    implementation(libs.hilt.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    kapt(libs.hilt.compiler)
    //Room
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    //Mock
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    implementation(libs.retromock)
    implementation(libs.retrofit.mock)
    //fingerPrint
    implementation(libs.androidx.biometric)
    //Test
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    //Corrutinas
    implementation(libs.kotlinx.coroutines.android)
    //Jetpack Compose
    implementation(libs.androidx.activity.compose.v130)
    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.ui.tooling)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.webkit)

    //Ktor
    implementation ("io.ktor:ktor-client-core:2.3.11")
    implementation ("io.ktor:ktor-client-android:2.3.11")
    implementation ("io.ktor:ktor-client-json:2.3.11")
    implementation ("io.ktor:ktor-client-serialization:2.3.11")
    implementation ("io.ktor:ktor-client-logging:2.3.11")
}