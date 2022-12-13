package net.azarquiel.alltricks.model

import android.app.Application
import androidx.lifecycle.LiveData

class BikeRepository(application: Application) {
    private val bikeDAO = AlltrickDB.getDB(application).bikeDAO()

    fun getByBrandId(brandID: Int): LiveData<List<BikeListView>> = bikeDAO.getByBrandId(brandID)
    fun getById(id: Int): LiveData<List<BikeDetailedView>> = bikeDAO.getById(id)
    fun update(bike: Bike) = bikeDAO.update(bike)
}

class BrandRepository(application: Application) {
    private val brandDAO = AlltrickDB.getDB(application).brandDAO()

    fun getAll(): LiveData<List<Brand>> = brandDAO.getAll()
}
