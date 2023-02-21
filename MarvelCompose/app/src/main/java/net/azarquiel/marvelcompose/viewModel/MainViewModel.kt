package net.azarquiel.marvelcompose.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.azarquiel.marvelcompose.api.MainRepository
import net.azarquiel.marvelcompose.model.Hero
import net.azarquiel.marvelcompose.model.UserData

class MainViewModel : ViewModel() {
    private var repository = MainRepository()

    val heroes: MutableLiveData<List<Hero>> = MutableLiveData()
        get() {
            if (field.value == null)
                viewModelScope.launch { field.value = repository.getHeroes() }
            return field
        }

    val selectedHero: MutableLiveData<Hero> = MutableLiveData()
    val selectedHeroRate: MutableLiveData<Int> = MutableLiveData()
    fun setSelectedHero(hero: Hero) {
        this.selectedHero.value = hero
        viewModelScope.launch { selectedHeroRate.value = repository.getAvgRate(hero.id); println(selectedHeroRate.value) }
    }

    fun reloadHeroes() = viewModelScope.launch { heroes.value = repository.getHeroes() }

    val login: MutableLiveData<UserData> = MutableLiveData()

    fun login(nick: String, pass: String) {
        viewModelScope.launch {
             repository.login(nick, pass).let {
                 if (it == null) register(nick, pass)
                 else login.value = it
            }
        }
    }

    fun logout() { login.value = null }

    private fun register(nick: String, pass: String) {
        viewModelScope.launch { login.value = repository.register(nick, pass) }
    }
}
