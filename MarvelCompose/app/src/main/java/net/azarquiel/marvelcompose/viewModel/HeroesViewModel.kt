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
import net.azarquiel.marvelcompose.di.LoginDS
import net.azarquiel.marvelcompose.model.Hero

interface IHeroesViewModel : ILoginCheck {
    val heroes: LiveData<List<Hero>>
}

class HeroesViewModel @AssistedInject constructor(
    private val loginDs: DataStore<Preferences>,
    @Assisted private val loginFn: () -> Unit,
) : ViewModel(), IHeroesViewModel {
    override val isLoggedIn = LoginDS.Utils.getIsLoggedInFlow(loginDs)

    override fun login() = loginFn()

    override fun logout() {
        viewModelScope.launch { LoginDS.Utils.logout(loginDs) }
    }

    private val heroes_: MutableLiveData<List<Hero>> = MutableLiveData()
    override val heroes: LiveData<List<Hero>> = heroes_

    init {
        viewModelScope.launch { heroes_.value = MarvelRepository.getHeroes() }
    }

    @AssistedFactory
    interface Factory {
        fun create(loginFn: () -> Unit): HeroesViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            loginFn: () -> Unit
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(loginFn) as T
            }
        }
    }
}
