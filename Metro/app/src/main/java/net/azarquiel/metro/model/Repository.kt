package net.azarquiel.metro.model

import android.app.Application
import androidx.lifecycle.LiveData

class LineRepository(application: Application) {
    private val lineDAO = MetroDB.getDB(application).lineDAO()

    fun getAll(): LiveData<List<LineView>> = lineDAO.getAll()
}

class StationRepository(application: Application) {
    private val stationDAO = MetroDB.getDB(application).stationDAO()

    fun getById(id: Int): LiveData<List<Station>> = stationDAO.getById(id)
}
