package net.azarquiel.caravanas.api

import net.azarquiel.caravanas.model.Community

class MainRepository() {
    val service = WebAccess.birdsService

    suspend fun getCommunities(): List<Community> {
        service.getCommunities().await().let { if (it.isSuccessful) return it.body()!!.data }
        return emptyList()
    }
}