package net.azarquiel.metro.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.metro.model.*

class LineViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = LineRepository(application)

    fun getAll(): LiveData<List<LineView>> = repository.getAll()
}

class StationViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = StationRepository(application)

    fun getByLine(line: Int): LiveData<Map<String, List<Int>>> = repository.getByLine(line)
}
