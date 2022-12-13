package net.azarquiel.alltricks.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.alltricks.model.*

class BikeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BikeRepository(application)

    fun getByBrandId(brandID: Int): LiveData<List<BikeListView>> = repository.getByBrandId(brandID)
    fun getById(id: Int): LiveData<List<BikeDetailedView>> = repository.getById(id)
    fun update(bike: Bike) = GlobalScope.launch { repository.update(bike) }
}

class BrandViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BrandRepository(application)

    fun getAll(): LiveData<List<Brand>> = repository.getAll()
}
