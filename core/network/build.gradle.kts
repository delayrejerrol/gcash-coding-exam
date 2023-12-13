plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.jnda.network"
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
            getByName(variantName).java.srcDir("build/generated/ksp/$variantName/kotlin")
        }
    }
}

dependencies {
    val coreKtxVersion = rootProject.extra["core_ktx_version"]
    api("androidx.core:core-ktx:$coreKtxVersion")

//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
//    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    val gsonVersion = rootProject.extra["gson_version"]
    api("com.google.code.gson:gson:$gsonVersion")
//    api("com.squareup.retrofit2:retrofit:2.9.0")
//    api("com.squareup.retrofit2:converter-gson:2.9.0")
//    api("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
//    api("com.trello.rxlifecycle2:rxlifecycle-components:2.1.0")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    api("com.squareup.retrofit2:retrofit:2.9.0")

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
    // endregion
}