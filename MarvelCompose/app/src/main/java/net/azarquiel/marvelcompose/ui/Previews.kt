@file:Suppress("ObjectPropertyName")

package net.azarquiel.marvelcompose.ui

import android.app.Application
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.azarquiel.marvelcompose.model.Hero
import net.azarquiel.marvelcompose.model.MarvelImg
import net.azarquiel.marvelcompose.viewModel.IHeroDetailsViewModel
import net.azarquiel.marvelcompose.viewModel.IHeroesViewModel
import net.azarquiel.marvelcompose.viewModel.ILoginCheck
import net.azarquiel.marvelcompose.viewModel.ILoginViewModel
import kotlin.math.roundToInt

object Previews {
    val Hero = Hero(
        id = 1017100,
        name = "A-Bomb (HAS)",
        description = "Rick Jones has been Hulk's best bud since day one, but now he's more than a friend...he's a teammate! Transformed by a Gamma energy explosion, A-Bomb's thick, armored skin is just as strong and powerful as it is blue. And when he curls into action, he uses it like a giant bowling ball of destruction! ",
        img = MarvelImg(
            url = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16",
            extension = "jpg",
        )
    )

    val MainActivity = object : Application() { }

    @OptIn(DelicateCoroutinesApi::class)
    val HeroesViewModel = object : IHeroesViewModel {
        override val heroes = MutableStateFlow(List(5) { Hero })

        private val _status: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading )
        override val state = _status

        init {
            GlobalScope.launch {
                delay(2_000)
                _status.value = UiState.Success
            }
        }

        override fun onHeroClick(hero: Hero) { }

        private val _isLoggedIn = MutableStateFlow(false)
        override val isLoggedIn = _isLoggedIn

        override fun login() { _isLoggedIn.value = true }
        override fun logout() { _isLoggedIn.value = false }
    }

    val HeroDetailsViewModel = object : IHeroDetailsViewModel {
        override val hero: Flow<Hero?> = MutableStateFlow(Hero)

        private val ratings = mutableListOf(3)
        private val _avgRate = MutableStateFlow(3)
        override val avgRate = _avgRate

        private val _isLoggedIn = MutableStateFlow(true)
        override val isLoggedIn = _isLoggedIn

        private val _rate = MutableStateFlow(0)
        override val rate = _rate

        override fun rate(rate: Int) {
            _rate.value = rate
            ratings.add(rate)
            _avgRate.value = ratings.average().roundToInt()
        }

        override fun login() { _isLoggedIn.value = true }
        override fun logout() { _isLoggedIn.value = false }
    }

    val LoginViewModel = object : ILoginViewModel {
        private val _nick = MutableStateFlow("")
        override val nick = _nick
        override fun onNickChange(v: String) { _nick.value = v }

        private val _pass = MutableStateFlow("")
        override val pass = _pass
        override fun onPassChange(v: String) { _pass.value = v }

        override fun onSubmit() { }
    }

    val LoginCheck = object : ILoginCheck {
        private val _isLoggedIn = MutableStateFlow(false)
        override val isLoggedIn = _isLoggedIn

        override fun login() { _isLoggedIn.value = true }
        override fun logout() { _isLoggedIn.value = false }
    }
}
