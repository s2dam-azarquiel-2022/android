package net.azarquiel.marvelcompose.di

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import net.azarquiel.marvelcompose.model.Hero
import net.azarquiel.marvelcompose.viewModel.HeroDetailsViewModel
import net.azarquiel.marvelcompose.viewModel.HeroesViewModel
import net.azarquiel.marvelcompose.viewModel.LoginViewModel

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun heroesViewModelFactory(): HeroesViewModel.Factory
    fun heroDetailsViewModelFactory(): HeroDetailsViewModel.Factory
    fun loginViewModelFactory(): LoginViewModel.Factory
}

@Suppress("NOTHING_TO_INLINE")
object ViewModels {
    @Composable
    inline fun heroesViewModel(
        noinline loginFn: () -> Unit,
        noinline onHeroClickFn: (Hero) -> Unit,
    ) = viewModel<HeroesViewModel>(
        factory = HeroesViewModel.provideFactory(
            assistedFactory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ViewModelFactoryProvider::class.java
            ).heroesViewModelFactory(),
            loginFn = loginFn,
            onHeroClickFn = onHeroClickFn,
        )
    )

    @Composable
    inline fun heroDetailsViewModel(
        heroId: String,
        noinline loginFn: () -> Unit,
    ) = viewModel<HeroDetailsViewModel>(
        factory = HeroDetailsViewModel.provideFactory(
            assistedFactory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ViewModelFactoryProvider::class.java
            ).heroDetailsViewModelFactory(),
            heroId = heroId,
            loginFn = loginFn,
        )
    )

    @Composable
    inline fun loginViewModel(
        noinline successfulLoginFn: () -> Unit,
        noinline unsuccessfulLoginFn: (String) -> Unit,
    ) = viewModel<LoginViewModel>(
        factory = LoginViewModel.provideFactory(
            assistedFactory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ViewModelFactoryProvider::class.java
            ).loginViewModelFactory(),
            successfulLoginFn = successfulLoginFn,
            unsuccessfulLoginFn = unsuccessfulLoginFn,
        )
    )
}
