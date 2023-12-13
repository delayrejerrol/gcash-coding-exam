plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.jnda.common"
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
    api(project(":core:network"))
    api(project(":core:storage"))

    val coreKtxVersion = rootProject.extra["core_ktx_version"]
    api("androidx.core:core-ktx:$coreKtxVersion")
    api("androidx.appcompat:appcompat:1.6.1")
    api("com.google.android.material:material:1.10.0")
    api("androidx.constraintlayout:constraintlayout:2.1.4")
    api("androidx.navigation:navigation-fragment-ktx:2.7.5")
    api("androidx.navigation:navigation-ui-ktx:2.7.5")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    ksp("com.github.bumptech.glide:ksp:4.14.2")

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