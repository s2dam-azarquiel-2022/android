package net.azarquiel.caravanas.api

import net.azarquiel.caravanas.model.*

class MainRepository() {
    val service = WebAccess.birdsService

    suspend fun getCommunities(): List<Community> {
        service.getCommunities().await().let { if (it.isSuccessful) return it.body()!!.data }
        return emptyList()
    }

    suspend fun getProvinces(id: String): List<Province>? {
        service.getProvinces(id).await().let { if (it.isSuccessful) return it.body()!!.data }
        return null
    }

    suspend fun getTowns(id: String): List<Town>? {
        service.getTowns(id).await().let { if (it.isSuccessful) return it.body()!!.data }
        return null
    }

    suspend fun getParkings(lat: String, lon: String): List<Parking>? {
        service.getParkings(lat, lon).await().let { if (it.isSuccessful) return it.body()!!.data }
        return null
    }

    suspend fun getPhotos(id: String): List<Photo>? {
        service.getPhotos(id).await().let { if (it.isSuccessful) return it.body()!!.data }
        return null
    }

    suspend fun getAvgRate(id: String): Float? {
        service.getAvgRate(id).await().let { if (it.isSuccessful) return it.body()!!.data }
        return null
    }
}