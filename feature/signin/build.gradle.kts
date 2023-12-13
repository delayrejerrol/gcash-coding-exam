plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.jnda.signin"
    val extraCompileSdk = rootProject.extra["compile_sdk"] as Int
    val extraMinSdk = rootProject.extra["min_sdk"] as Int
    compileSdk = extraCompileSdk

    defaultConfig {
        minSdk = extraMinSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    // For KSP
    libraryVariants.all {
        val variantName = name
        sourceSets {
            getByName("main").java.srcDir("build/generated/ksp/$variantName/kotlin")
        }
    }
}

dependencies {
    implementation(project(":core:common"))

    // koin
    val koinAndroid = rootProject.extra["koin_android"]
    implementation("io.insert-koin:koin-android:$koinAndroid")
    val koinAnnotationsVersion = rootProject.extra["koin_annotations_version"]
    implementation("io.insert-koin:koin-annotations:$koinAnnotationsVersion")
    ksp("io.insert-koin:koin-ksp-compiler:$koinAnnotationsVersion")

    // region Test
    // JUNIT and ESPRESSO
    val junitVersion = rootProject.extra["junit_version"]
    testImplementation("junit:junit:$junitVersion")
    val androidXJUnitVersion = rootProject.extra["androidx_test_junit_version"]
    androidTestImplementation("androidx.test.ext:junit:$androidXJUnitVersion")
    val androidXEspressoVersion = rootProject.extra["androidx_test_espresso_version"]
    androidTestImplementation("androidx.test.espresso:espresso-core:$androidXEspressoVersion")
    // KOIN
    testImplementation("io.insert-koin:koin-test:$koinAndroid")
    testImplementation("io.insert-koin:koin-test-junit4:$koinAndroid")
    testImplementation("io.insert-koin:koin-test-junit5:$koinAndroid")
    // MOCKITO
    val mockitoCoreVersion = rootProject.extra["mockito_core_version"]
    testImplementation("org.mockito:mockito-core:$mockitoCoreVersion")
    val mockitoKotlinVersion = rootProject.extra["mockito_kotlin_version"]
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    val mockitoAndroidVersion = rootProject.extra["mockito_android_version"]
    androidTestImplementation("org.mockito:mockito-android:$mockitoAndroidVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    // endregion
}