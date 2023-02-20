package net.azarquiel.marvelcompose.api

import kotlinx.coroutines.Deferred
import net.azarquiel.marvelcompose.model.UserData
import net.azarquiel.marvelcompose.model.ZResponse
import retrofit2.Response

class MainRepository {
    private suspend inline fun <T, D: ZResponse<out T>> get(
        f: () -> Deferred<Response<D>>,
        default: () -> T,
    ): T = f().await().let { if (it.isSuccessful) it.body()?.data ?: default() else default() }

    suspend fun login(nick: String, pass: String): UserData? =
        get({ WebAccess.service.login(nick, pass) }) { null }

    suspend fun register(nick: String, pass: String): UserData? =
        get({ WebAccess.service.register(nick, pass) }) { null }
}
