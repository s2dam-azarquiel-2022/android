package net.azarquiel.foster.model

import android.app.Application
import androidx.lifecycle.LiveData

class CategoryRepository(application: Application) {
    private val categoryDAO = FosterDB.getDB(application).categoryDAO()

    fun getAll(): LiveData<List<Category>> = categoryDAO.getAll()
}
class ProductRepository(application: Application) {
    private val productDAO = FosterDB.getDB(application).productDAO()

    fun getByCategoryID(categoryID: Int): LiveData<List<ProductDetailedView>> =
        productDAO.getByCategoryID(categoryID)
}
