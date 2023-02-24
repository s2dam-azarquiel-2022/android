package net.azarquiel.marvelcompose.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import net.azarquiel.marvelcompose.model.Hero
import net.azarquiel.marvelcompose.model.MarvelImg
import net.azarquiel.marvelcompose.model.UserData
import net.azarquiel.marvelcompose.viewModel.IHeroesViewModel
import net.azarquiel.marvelcompose.viewModel.ILoginCheck
import net.azarquiel.marvelcompose.viewModel.ILoginViewModel

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

    val HeroesViewModel = object : IHeroesViewModel {
        override val heroes: LiveData<List<Hero>> = MutableLiveData(List(5) { Hero })

        private val isLoggedIn_ = MutableStateFlow(false)
        override val isLoggedIn = isLoggedIn_

        override fun login() { isLoggedIn_.value = true }
        override fun logout() { isLoggedIn_.value = false }
    }

    val LoginViewModel = object : ILoginViewModel {
        private val login_: MutableLiveData<UserData> = MutableLiveData()
        val login: LiveData<UserData> = login_

        override fun login(nick: String, pass: String) {
            login_.value = UserData(id = "0", nick = nick)
        }

        init { login("0", "aru") }
    }

    val LoginCheck = object : ILoginCheck {
        private val isLoggedIn_ = MutableStateFlow(false)
        override val isLoggedIn = isLoggedIn_

        override fun login() { isLoggedIn_.value = true }
        override fun logout() { isLoggedIn_.value = false }
    }
}
