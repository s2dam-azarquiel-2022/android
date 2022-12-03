package net.azarquiel.metro.model

import android.app.Application
import androidx.lifecycle.LiveData

class LineRepository(application: Application) {
    private val lineDAO = MetroDB.getDB(application).lineDAO()

    fun getAll(): LiveData<List<LineView>> = lineDAO.getAll()
}

class StationRepository(application: Application) {
    private val stationDAO = MetroDB.getDB(application).stationDAO()

    fun getByLine(line: Int): LiveData<Map<String, List<Int>>> = stationDAO.getByLine(line)
}
