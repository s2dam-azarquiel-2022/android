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
import net.azarquiel.marvelcompose.ui.UiState

interface IHeroesViewModel : ILoginCheck, ILoading {
    val heroes: StateFlow<List<Hero>>

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
    private val _heroes = MutableStateFlow(emptyList<Hero>())
    override val heroes = _heroes

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    override val state = _state

    init {
        viewModelScope.launch {
            MarvelRepository.getHeroes().let {
                if (it == null) _state.value = UiState.Error
                else {
                    _heroes.value = it
                    _state.value = UiState.Success
                }
            }
        }
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
