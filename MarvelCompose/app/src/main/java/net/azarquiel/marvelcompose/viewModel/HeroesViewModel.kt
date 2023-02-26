package net.azarquiel.marvelcompose.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.azarquiel.marvelcompose.api.MarvelRepository
import net.azarquiel.marvelcompose.di.HeroesDS
import net.azarquiel.marvelcompose.di.HeroesDSProvider
import net.azarquiel.marvelcompose.di.LoginDSProvider
import net.azarquiel.marvelcompose.model.Hero

interface IHeroesViewModel : ILoginCheck {
    val heroes: LiveData<List<Hero>>

    fun onHeroClick(hero: Hero)
}

class HeroesViewModel @AssistedInject constructor(
    @LoginDSProvider private val loginDS: DataStore<Preferences>,
    @HeroesDSProvider private val heroesDS: DataStore<Preferences>,
    @Assisted loginFn: () -> Unit,
    @Assisted private val onHeroClickFn: (Hero) -> Unit,
) : LoginCheckViewModel(
    loginDS = loginDS,
    loginFn = loginFn,
), IHeroesViewModel {
    private val _heroes: MutableLiveData<List<Hero>> = MutableLiveData()
    override val heroes: LiveData<List<Hero>> = _heroes

    init {
        viewModelScope.launch { _heroes.value = MarvelRepository.getHeroes() }
    }

    override fun onHeroClick(hero: Hero) {
        viewModelScope.launch {
            HeroesDS.Utils.storeHero(heroesDS, hero = hero)
            onHeroClickFn(hero)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            loginFn: () -> Unit,
            onHeroClickFn: (Hero) -> Unit,
        ): HeroesViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            loginFn: () -> Unit,
            onHeroClickFn: (Hero) -> Unit,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(
                    loginFn = loginFn,
                    onHeroClickFn = onHeroClickFn
                ) as T
        }
    }
}
