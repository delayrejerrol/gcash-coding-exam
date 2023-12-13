plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.jnda.storage"
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

    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")

//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
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