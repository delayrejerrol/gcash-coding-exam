plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.jnda.weatherapp"
    val extraCompileSdk = rootProject.extra["compile_sdk"] as Int
    val extraMinSdk = rootProject.extra["min_sdk"] as Int
    compileSdk = extraCompileSdk

    defaultConfig {
        applicationId = "com.jnda.weatherapp"
        minSdk = extraMinSdk
        targetSdk = extraCompileSdk
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
        getByName("test").java.srcDir("src/test/kotlin")
    }

    buildFeatures {
        viewBinding = true
    }

    // For KSP
    applicationVariants.all {
        val variantName = name
        sourceSets {
            getByName("main").java.srcDir("build/generated/ksp/$variantName/kotlin")
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:signin"))
    implementation(project(":feature:home"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // koin
    val koinAndroid = rootProject.extra["koin_android"]
    implementation("io.insert-koin:koin-android:$koinAndroid")
    val koinAnnotationsVersion = rootProject.extra["koin_annotations_version"]
    implementation("io.insert-koin:koin-annotations:$koinAnnotationsVersion")
    ksp("io.insert-koin:koin-ksp-compiler:$koinAnnotationsVersion")
}