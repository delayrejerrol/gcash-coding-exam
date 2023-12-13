package com.jnda.common.provider

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import com.jnda.common.R
import org.koin.core.annotation.Single

@Single
class NavigationProviderImpl(
    private val resourceProvider: ResourceProvider
) : NavigationProvider {

    private fun buildDeepLink(uri: String): NavDeepLinkRequest =
        NavDeepLinkRequest.Builder
            .fromUri(uri.toUri())
            .build()

    override fun navigateToDestination(deepLink: String): NavDeepLinkRequest =
        buildDeepLink(deepLink)

    override fun navigateToSignInScreen(): NavDeepLinkRequest =
        buildDeepLink(resourceProvider.getString(R.string.deeplink_sign_in))

    override fun navigateToSignUpScreen(): NavDeepLinkRequest =
        buildDeepLink(resourceProvider.getString(R.string.deeplink_sign_up))

    override fun navigateToHomeScreen(): NavDeepLinkRequest =
        buildDeepLink(resourceProvider.getString(R.string.deeplink_home))

    override fun navigateToMainWeatherScreen(): NavDeepLinkRequest =
        buildDeepLink(resourceProvider.getString(R.string.deeplink_main_weather))

    override fun navigateToWeatherListScreen(): NavDeepLinkRequest =
        buildDeepLink(resourceProvider.getString(R.string.deeplink_weather_list))
}