package net.azarquiel.foster.viemModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.foster.model.*

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CategoryRepository(application)

    fun getAll(): LiveData<List<Category>> = repository.getAll()
}

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProductRepository(application)

    fun getByCategoryID(categoryID: Int): LiveData<List<ProductListView>> = repository.getByCategoryID(categoryID)
    fun getByID(id: Int): LiveData<List<ProductDetailedView>> = repository.getByID(id)
}