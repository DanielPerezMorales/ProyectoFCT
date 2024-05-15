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
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    //Room
    implementation(libs.androidx.room.ktx)
    kapt("androidx.room:room-compiler:2.6.1")
    //Mock
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:3.12.4")
    implementation("co.infinum:retromock:1.1.0")
    implementation("com.squareup.retrofit2:retrofit-mock:2.9.0")
    //fingerPrint
    implementation(libs.androidx.biometric)
    //Test
    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation(libs.junit)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    //Corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //Jetpack Compose
    implementation(libs.androidx.activity.compose.v130)
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.material:material:1.6.7")
    implementation("androidx.compose.ui:ui-tooling:1.6.7")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")
    implementation("androidx.compose.foundation:foundation:1.6.7")
    implementation("androidx.compose.foundation:foundation-layout:1.6.7")
    implementation(libs.androidx.navigation.compose)

    implementation ("androidx.webkit:webkit:1.11.0")
}