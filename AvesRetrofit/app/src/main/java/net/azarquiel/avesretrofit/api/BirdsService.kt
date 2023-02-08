package net.azarquiel.avesretrofit.api

import kotlinx.coroutines.Deferred
import net.azarquiel.avesretrofit.model.*
import retrofit2.Response
import retrofit2.http.*

interface BirdsService {
    @GET("zonas")
    fun getZones(): Deferred<Response<Zones>>
}