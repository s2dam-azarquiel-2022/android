package net.azarquiel.marvelcompose.navigation

sealed class Destinations(val route: String) {
    object HeroList: Destinations("hero_list")
    object HeroDetails: Destinations("hero_details")
    object Login: Destinations("login")
}
