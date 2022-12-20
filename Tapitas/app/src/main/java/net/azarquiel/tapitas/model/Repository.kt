package net.azarquiel.tapitas.model

import android.app.Application
import androidx.lifecycle.LiveData

class TapaRepository(application: Application) {
    private val tapaDAO = TapasDB.getDB(application).tapaDAO()

    fun getAll(): LiveData<List<TapaView>> = tapaDAO.getAll()
    fun getById(id: Int): LiveData<List<TapaDetailedView>> = tapaDAO.getById(id)
    fun toggleFav(id: Int) = tapaDAO.toggleFav(id)
}

class StablishmentRepository(application: Application) {
    private val stablishmentDAO = TapasDB.getDB(application).stablishmentDAO()

    fun getById(id: Int): LiveData<List<StablishmentView>> = stablishmentDAO.getById(id)
    fun getRecipesById(id: Int): LiveData<List<String>> = stablishmentDAO.getRecipesById(id)
}