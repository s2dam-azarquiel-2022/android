package net.azarquiel.avesretrofit.api

import kotlinx.coroutines.Deferred
import net.azarquiel.avesretrofit.model.*
import retrofit2.Response
import retrofit2.http.*

interface BirdsService {
    @GET("zonas")
    fun getZones(): Deferred<Response<Zones>>

    @GET("zona/{id}/recursos")
    fun getZoneResources(
        @Path("id") id: String
    ): Deferred<Response<Resources>>

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

    @GET("recurso/{id}/comentarios")
    fun getResourceComments(
        @Path("id") id: String
    ): Deferred<Response<Comments>>

    @POST("recurso/{id}/comentario")
    fun addResourceComment(
        @Path("id") id: String,
        @Query("usuario") userID: String,
        @Query("comentario") comment: String,
        @Query("fecha") date: String,
    ): Deferred<Response<Comment>>
}