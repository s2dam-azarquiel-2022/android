package net.azarquiel.marvelcompose.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.azarquiel.marvelcompose.di.LoginDS
import net.azarquiel.marvelcompose.ui.UiState

// Used by view models which need to check login state and support the action to login and logout
// In this case the login action is to navigate to the LoginScreen while logout does so in-place
interface ILoginCheck {
    val isLoggedIn: Flow<Boolean>
    fun login()
    fun logout()
}

open class LoginCheckViewModel(
    private val loginDS: DataStore<Preferences>,
    private val loginFn: () -> Unit,
) : ViewModel(), ILoginCheck {
    override val isLoggedIn = LoginDS.Utils.getIsLoggedInFlow(loginDS)
    override fun login() = loginFn()
    override fun logout() {
        viewModelScope.launch { LoginDS.Utils.logout(loginDS) }
    }
}

interface ILoading {
    val state: StateFlow<UiState>
}
