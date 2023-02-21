package net.azarquiel.marvelcompose.api

import net.azarquiel.marvelcompose.model.User
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import net.azarquiel.marvelcompose.model.AvgRate
import net.azarquiel.marvelcompose.model.MarvelResults
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
