package net.azarquiel.marvelcompose.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.azarquiel.marvelcompose.api.MarvelRepository
import net.azarquiel.marvelcompose.di.HeroesDS
import net.azarquiel.marvelcompose.di.HeroesDSProvider
import net.azarquiel.marvelcompose.di.LoginDSProvider
import net.azarquiel.marvelcompose.model.Hero

interface IRateViewModel {
    val rate: StateFlow<Int>
    fun rate(rate: Int)
}

interface IHeroDetailsViewModel : ILoginCheck, IRateViewModel {
    val hero: Flow<Hero?>
    val avgRate: StateFlow<Int>
}

class HeroDetailsViewModel @AssistedInject constructor(
    @LoginDSProvider private val loginDS: DataStore<Preferences>,
    @HeroesDSProvider private val heroesDS: DataStore<Preferences>,
    @Assisted private val heroId: String,
    @Assisted loginFn: () -> Unit,
) : LoginCheckViewModel(
    loginDS = loginDS,
    loginFn = loginFn,
), IHeroDetailsViewModel {
    override val hero = HeroesDS.Utils.getHeroFlow(heroesDs = heroesDS, heroId = heroId)

    private val _avgRate = MutableStateFlow(0)
    override val avgRate = _avgRate

    private val _rate = MutableStateFlow(0)
    override val rate = _rate

    override fun rate(rate: Int) {
        _rate.value = rate
        viewModelScope.launch {
            MarvelRepository.rate(heroId, rate)
            getAvgRate()
        }
    }

    init {
        viewModelScope.launch { getAvgRate() }
    }

    private suspend fun getAvgRate() {
        _avgRate.value = MarvelRepository.getAvgRate(heroId)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            heroId: String,
            loginFn: () -> Unit,
        ): HeroDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            heroId: String,
            loginFn: () -> Unit,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(
                    heroId = heroId,
                    loginFn = loginFn,
                ) as T
        }
    }
}
