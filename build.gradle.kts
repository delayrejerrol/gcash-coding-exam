// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("compile_sdk", 34)
        set("target_sdk", 34)
        set("min_sdk", 28)
        set("core_ktx_version", "1.12.0")
        set("appcompat_version", "1.6.1")

        // Koin annotations
        set("koin_android", "3.4.1")
        set("koin_annotations_version", "1.2.1")

        set("gson_version", "2.9.1")

        // unit test
        set("junit_version", "4.13.2")
        set("androidx_test_junit_version", "1.1.5")
        set("androidx_test_espresso_version", "3.5.1")
        set("mockito_core_version", "5.3.1")
        set("mockito_kotlin_version", "3.2.0")
        set("mockito_android_version", "2.24.5")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}