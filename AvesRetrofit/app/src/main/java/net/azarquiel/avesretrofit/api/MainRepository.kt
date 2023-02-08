package net.azarquiel.avesretrofit.api

import net.azarquiel.avesretrofit.model.Resource
import net.azarquiel.avesretrofit.model.Zone

class MainRepository() {
    val service = WebAccess.birdsService

    suspend fun getZones(): List<Zone> {
        service.getZones().await().let { if (it.isSuccessful) return it.body()!!.zones }
        return emptyList()
    }
}