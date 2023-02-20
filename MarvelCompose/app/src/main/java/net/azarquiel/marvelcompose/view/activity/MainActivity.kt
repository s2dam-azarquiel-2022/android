package net.azarquiel.marvelcompose.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.marvelcompose.view.ui.items.HeroList
import net.azarquiel.marvelcompose.view.ui.theme.MarvelComposeTheme
import net.azarquiel.marvelcompose.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    private fun initVars() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVars()
        setContent { MarvelComposeTheme { Paint() } }
    }

    @Composable
    private fun Paint() {
        HeroList(mainViewModel.getHeroes())
    }
}
