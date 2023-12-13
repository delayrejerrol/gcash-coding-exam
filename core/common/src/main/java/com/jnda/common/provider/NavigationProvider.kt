package com.jnda.common.provider

import androidx.navigation.NavDeepLinkRequest

interface NavigationProvider {
    fun navigateToDestination(deepLink: String) : NavDeepLinkRequest
    fun navigateToSignInScreen() : NavDeepLinkRequest
    fun navigateToSignUpScreen() : NavDeepLinkRequest
    fun navigateToHomeScreen() : NavDeepLinkRequest

    fun navigateToMainWeatherScreen() : NavDeepLinkRequest

    fun navigateToWeatherListScreen() : NavDeepLinkRequest
}