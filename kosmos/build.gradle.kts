plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.kosmos.components"
    compileSdk {
        version = release(37)
    }

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material:material-icons-core")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}