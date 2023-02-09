package net.azarquiel.avesretrofit.api

import net.azarquiel.avesretrofit.model.*

class MainRepository() {
    val service = WebAccess.birdsService

    suspend fun getZones(): List<Zone> {
        service.getZones().await().let { if (it.isSuccessful) return it.body()!!.zones }
        return emptyList()
    }

    suspend fun getZoneResources(id: String): List<Resource>? {
        service.getZoneResources(id).await().let { if (it.isSuccessful) return it.body()!!.resources }
        return null
    }

    suspend fun getResourceComments(id: String): List<CommentData>? {
        service.getResourceComments(id).await().let { if (it.isSuccessful) return it.body()!!.comments }
        return null
    }

    suspend fun login(nick: String, pass: String): UserData? {
        service.login(nick, pass).await().let { if (it.isSuccessful) return it.body()!!.data }
        return null
    }

    suspend fun register(nick: String, pass: String): UserData {
        service.register(nick, pass).await().let { return it.body()!!.data!! }
    }
}