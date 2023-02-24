package net.azarquiel.marvelcompose.ui.navigation

sealed class Destination(
    val route: String,
) {
    object HeroList: Destination("hero_list")
    object HeroDetails: Destination("hero_details")
    object Login: Destination("login")
}
