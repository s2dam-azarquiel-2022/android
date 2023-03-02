package net.azarquiel.chistes.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import net.azarquiel.chistes.model.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JokesAPI {
    @GET("categorias")
    fun getCategories(): Deferred<Response<CategoriesResponse>>

    @GET("categoria/{id}/chistes")
    fun getJokes(
        @Path("id") id: String,
    ): Deferred<Response<JokesResponse>>

    @POST("chiste")
    fun addJoke(
        @Query("idcategoria") id: String,
        @Query("contenido") text: String,
    ): Deferred<Response<JokeResponse>>

    @GET("chiste/{id}/avgpuntos")
    fun getAvg(
        @Path("id") id: String,
    ): Deferred<Response<AvgResponse>>

    @POST("chiste/{id}/punto")
    fun rate(
        @Path("id") id: String,
        @Body rating: Rating,
    ): Deferred<Response<RatingResponse>>

    @GET("usuario")
    fun login(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<UserResponse>>

    @POST("usuario")
    fun register(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<UserResponse>>

    companion object {
        val baseUrl = "http://www.ies-azarquiel.es/paco/apichistes/"
        val service: JokesAPI by lazy {
            return@lazy Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(baseUrl)
                .build()
                .create(JokesAPI::class.java)
        }
    }
}

object JokesRepository {
    private suspend inline fun <T, D: ZResponse<out T>> get(
        f: () -> Deferred<Response<D>>,
        default: () -> T,
    ): T = f().await().let { if (it.isSuccessful) it.body()?.data ?: default() else default() }

    suspend fun getCategories(): List<Category> =
        get(JokesAPI.service::getCategories) { emptyList() }

    suspend fun getJokes(id: String): MutableList<Joke> =
        get({ JokesAPI.service.getJokes(id) }) { mutableListOf() }

    suspend fun addJoke(id: String, text: String): Joke? =
        get({ JokesAPI.service.addJoke(id, text) }) { null }

    suspend fun rate(id: String, rating: Int): Rating? =
        get({ JokesAPI.service.rate(id, Rating(id, rating)) }) { null }

    suspend fun getAvg(id: String): Int =
        get({ JokesAPI.service.getAvg(id) }) { 0 }

    suspend fun login(nick: String, pass: String): User? =
        get({ JokesAPI.service.login(nick, pass) }) { null }

    suspend fun register(nick: String, pass: String): User? =
        get({ JokesAPI.service.register(nick, pass) }) { null }

}
