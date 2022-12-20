package net.azarquiel.tapitas.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.tapitas.model.*

class TapaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TapaRepository(application)

    fun getAll(): LiveData<List<TapaView>> = repository.getAll()
    fun getById(id: Int): LiveData<List<TapaDetailedView>> = repository.getById(id)

    @OptIn(DelicateCoroutinesApi::class)
    fun toggleFav(id: Int) = GlobalScope.launch { repository.toggleFav(id) }
}

class StablishmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StablishmentRepository(application)

    fun getById(id: Int): LiveData<List<StablishmentView>> = repository.getById(id)
    fun getRecipesById(id: Int): LiveData<List<String>> = repository.getRecipesById(id)
}
