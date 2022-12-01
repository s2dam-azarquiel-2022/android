package net.azarquiel.metro.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import net.azarquiel.metro.model.*

@OptIn(DelicateCoroutinesApi::class)
class LineViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = LineRepository(application)

    fun getAll(): LiveData<List<LineView>> = repository.getAll()
}
@OptIn(DelicateCoroutinesApi::class)
class StationViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = StationRepository(application)

    fun getById(id: Int): LiveData<List<Station>> = repository.getById(id)
}
