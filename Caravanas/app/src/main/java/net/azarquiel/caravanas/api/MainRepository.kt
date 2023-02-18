package net.azarquiel.caravanas.api

import kotlinx.coroutines.Deferred
import net.azarquiel.caravanas.model.*
import retrofit2.Response

class MainRepository {
    private suspend inline fun <T, D: DataHolder<out T>> get(
        f: () -> Deferred<Response<D>>,
        default: () -> T,
    ): T = f().await().let { if (it.isSuccessful) it.body()?.data ?: default() else default() }

    suspend fun getCommunities(): List<Community> =
        get(WebAccess.service::getCommunities) { emptyList() }

    suspend fun getProvinces(id: String): List<Province>? =
        get({ WebAccess.service.getProvinces(id) }) { null }

    suspend fun getTowns(id: String): List<Town>? =
        get({ WebAccess.service.getTowns(id) }) { null }

    suspend fun getParkings(lat: String, lon: String): List<Parking>? =
        get({ WebAccess.service.getParkings(lat, lon) }) { null }

    suspend fun getPhotos(id: String): List<Photo>? =
        get({ WebAccess.service.getPhotos(id) }) { null }

    suspend fun getAvgRate(id: String): Float? =
        get({ WebAccess.service.getAvgRate(id) }) { null }

    suspend fun login(nick: String, pass: String): UserData? =
        get({ WebAccess.service.login(nick, pass) }) { null }

    suspend fun register(nick: String, pass: String): UserData? =
        get({ WebAccess.service.register(nick, pass) }) { null }

    suspend fun rate(
        id: String,
        user: String,
        rate: String,
    ): Boolean = WebAccess.service.rate(id, user, rate).await().isSuccessful
}