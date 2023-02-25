package net.azarquiel.marvelcompose.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.azarquiel.marvelcompose.api.MarvelRepository
import net.azarquiel.marvelcompose.di.LoginDS
import net.azarquiel.marvelcompose.di.LoginDSProvider
import net.azarquiel.marvelcompose.model.UserData

interface ILoginViewModel {
    val nick: StateFlow<String>
    fun onNickChange(v: String)

    val pass: StateFlow<String>
    fun onPassChange(v: String)

    fun onSubmit()
}

class LoginViewModel @AssistedInject constructor(
    @LoginDSProvider private val loginDS: DataStore<Preferences>,
    @Assisted private val successfulLoginFn: () -> Unit,
    @Assisted private val unsuccessfulLoginFn: (String) -> Unit,
) : ViewModel(), ILoginViewModel {
    private val nick_ = MutableStateFlow("")
    override val nick = nick_
    override fun onNickChange(v: String) {
        nick_.value = v
    }

    private val pass_ = MutableStateFlow("")
    override val pass = pass_
    override fun onPassChange(v: String) {
        pass_.value = v
    }

    override fun onSubmit() {
        if (nick_.value.isBlank() || pass_.value.isBlank()) {
            unsuccessfulLoginFn("Fill the nick and password fields")
            return
        }
        viewModelScope.launch {
            MarvelRepository.login(
                nick.value,
                pass.value
            )?.let { storeLogin(it) } ?: register()
        }
    }

    private fun register() {
        viewModelScope.launch {
            MarvelRepository.register(
                nick.value,
                pass.value
            )?.let { storeLogin(it) } ?: unsuccessfulLoginFn("Wrong password")
        }
    }

    private suspend fun storeLogin(user: UserData) {
        loginDS.edit {
            it[LoginDS.Field.IsLoggedIn.key] = true
            it[LoginDS.Field.Id.key] = user.id
            it[LoginDS.Field.Nick.key] = user.nick
        }
        successfulLoginFn()
    }

    @AssistedFactory
    interface Factory {
        fun create(
            successfulLoginFn: () -> Unit,
            unsuccessfulLoginFn: (String) -> Unit
        ): LoginViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            successfulLoginFn: () -> Unit,
            unsuccessfulLoginFn: (String) -> Unit,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(
                    successfulLoginFn = successfulLoginFn,
                    unsuccessfulLoginFn = unsuccessfulLoginFn
                ) as T
        }
    }
}
