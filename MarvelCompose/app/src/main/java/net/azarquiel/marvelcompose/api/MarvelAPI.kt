package net.azarquiel.marvelcompose.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import net.azarquiel.marvelcompose.model.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelAPI {
    @GET("hero")
    fun getHeroes(): Deferred<Response<MarvelResults>>

    @GET("hero/{id}/avgpuntos")
    fun getAvgRate(
        @Path("id") id: Long,
    ): Deferred<Response<AvgRate>>

    @GET("usuario")
    fun login(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<User>>

    @POST("usuario")
    fun register(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<User>>

    companion object {
        val service: MarvelAPI by lazy {
            return@lazy Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl("http://www.ies-azarquiel.es/paco/apimarvel/")
                .build()
                .create(MarvelAPI::class.java)
        }
    }
}

object MarvelRepository {
    private suspend inline fun <T, D: ZResponse<out T>> get(
        f: () -> Deferred<Response<D>>,
        default: () -> T,
    ): T = f().await().let { if (it.isSuccessful) it.body()?.data ?: default() else default() }

    suspend fun getHeroes(): List<Hero>? =
        get(MarvelAPI.service::getHeroes) { Heroes(null) }.data

    suspend fun getAvgRate(id: Long): Int =
        get({ MarvelAPI.service.getAvgRate(id) }) { 0 }

    suspend fun login(nick: String, pass: String): UserData? =
        get({ MarvelAPI.service.login(nick, pass) }) { null }

    suspend fun register(nick: String, pass: String): UserData? =
        get({ MarvelAPI.service.register(nick, pass) }) { null }
}
