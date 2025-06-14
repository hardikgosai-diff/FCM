plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.app.fcm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app.fcm"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.inappmessaging.display)

    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    // Optional for Kotlin coroutines
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //dagger-hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //navigation component
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    //ads
    implementation(libs.play.services.ads)

    //glide
    implementation(libs.glide)
    kapt(libs.compiler)


}