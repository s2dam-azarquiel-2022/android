package net.azarquiel.tapitas.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.tapitas.model.StablishmentRepository
import net.azarquiel.tapitas.model.TapaDetailedView
import net.azarquiel.tapitas.model.TapaRepository
import net.azarquiel.tapitas.model.TapaView

class TapaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TapaRepository(application)

    fun getAll(): LiveData<List<TapaView>> = repository.getAll()
    fun getById(id: Int): LiveData<List<TapaDetailedView>> = repository.getById(id)

    @OptIn(DelicateCoroutinesApi::class)
    fun toggleFav(id: Int) = GlobalScope.launch { repository.toggleFav(id) }
}

class StablishmentViewModel(application: Application) {
    private val repository = StablishmentRepository(application)
}
