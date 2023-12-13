# gcash-coding-exam

## Project Description

The project is developed using Kotlin, and Koin (DI)
it contains the following module

- :core:common - where the common components are declared
- :core:network - where the network components are declared
- :core:storage - where the room components are declared

- :feature:home - where the home screen is declared including the weather screens
- :feature:signin - where the sign-in screen is declared
- :feature:signup - where the sign-up screen is declared

Please put your WEATHER API KEY in `com.jnda.common.provider.WeatherAPIProviderImpl` class.

## Instruction

Please register an account first then login the registered account, registered account is just being save locally.
The location has default latitude and longitude, whenever you revoke the location permission it will still 
get a weather update. You must allow the location permission to get the actual weather update on your location.