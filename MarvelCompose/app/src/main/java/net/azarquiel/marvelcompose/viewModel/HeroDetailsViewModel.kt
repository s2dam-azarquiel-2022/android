package net.azarquiel.marvelcompose.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import net.azarquiel.marvelcompose.di.HeroesDS
import net.azarquiel.marvelcompose.di.HeroesDSProvider
import net.azarquiel.marvelcompose.di.LoginDSProvider
import net.azarquiel.marvelcompose.model.Hero

interface IHeroDetailsViewModel : ILoginCheck {
    val hero: Flow<Hero?>
}

class HeroDetailsViewModel @AssistedInject constructor(
    @LoginDSProvider private val loginDS: DataStore<Preferences>,
    @HeroesDSProvider private val heroesDS: DataStore<Preferences>,
    @Assisted heroId: String,
    @Assisted loginFn: () -> Unit,
) : LoginCheckViewModel(
    loginDS = loginDS,
    loginFn = loginFn,
), IHeroDetailsViewModel {
    override val hero = HeroesDS.Utils.getHeroFlow(heroesDs = heroesDS, heroId = heroId)

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
