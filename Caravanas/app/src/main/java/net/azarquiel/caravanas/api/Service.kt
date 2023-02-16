package net.azarquiel.caravanas.api

import kotlinx.coroutines.Deferred
import net.azarquiel.caravanas.model.*
import retrofit2.Response
import retrofit2.http.*

interface Service {
    @GET("comunidad")
    fun getCommunities(): Deferred<Response<Communities>>

    @GET("comunidad/{id}/provincia")
    fun getProvinces(
        @Path("id") id: String
    ): Deferred<Response<Provinces>>
}