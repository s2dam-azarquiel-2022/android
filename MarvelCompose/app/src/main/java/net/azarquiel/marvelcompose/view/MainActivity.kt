package net.azarquiel.marvelcompose.view

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import net.azarquiel.marvelcompose.ui.navigation.Destination
import net.azarquiel.marvelcompose.ui.screen.HeroesScreen
import net.azarquiel.marvelcompose.ui.screen.LoginScreen
import net.azarquiel.marvelcompose.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppTheme {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Destination.HeroList.route,
            ) {
                HeroesScreen(navController = navController)
                LoginScreen(navController = navController)
            }
        } }
    }
}

@HiltAndroidApp
class MarvelComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: MarvelComposeApp? = null
    }
}
