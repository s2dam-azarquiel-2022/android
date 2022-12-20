package net.azarquiel.tapitas.model

import android.app.Application
import androidx.lifecycle.LiveData

class TapaRepository(application: Application) {
    private val tapaDAO = TapasDB.getDB(application).tapaDAO()

    fun getAll(): LiveData<List<TapaView>> = tapaDAO.getAll()
}

class StablishmentRepository(application: Application) {
    private val stablishmentDAO = TapasDB.getDB(application).stablishmentDAO()
}