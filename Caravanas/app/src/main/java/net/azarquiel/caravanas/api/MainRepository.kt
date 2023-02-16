package net.azarquiel.caravanas.api

import net.azarquiel.caravanas.model.Community
import net.azarquiel.caravanas.model.Province

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
}