package net.azarquiel.tapitas.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.tapitas.model.StablishmentRepository
import net.azarquiel.tapitas.model.TapaRepository
import net.azarquiel.tapitas.model.TapaView

class TapaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TapaRepository(application)

    fun getAll(): LiveData<List<TapaView>> = repository.getAll()
}

class StablishmentViewModel(application: Application) {
    private val repository = StablishmentRepository(application)
}
