package net.azarquiel.marvelcompose.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import net.azarquiel.marvelcompose.model.Hero

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    object HeroList: Destination("hero_list")

    object HeroDetails: Destination(
        route = "hero_details/{heroId}",
        arguments = listOf(
            navArgument("heroId") { type = NavType.StringType }
        )
    ) { fun createRoute(hero: Hero) = "hero_details/${hero.id}" }

    object Login: Destination("login")
}
