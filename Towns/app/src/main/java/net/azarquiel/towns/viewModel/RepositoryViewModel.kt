package net.azarquiel.towns.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.towns.model.*

class CommunityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CommunityRepository(application)

    fun getAll(): LiveData<List<Community>> = repository.getAll()
}

class ProvinceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProvinceRepository(application)
}

class TownViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TownRepository(application)

    fun getByCommunityID(communityID: Int): LiveData<List<TownView>> =
        repository.getByCommunityID(communityID)
    fun getById(id: Int): LiveData<List<TownView>> = repository.getById(id)

    @OptIn(DelicateCoroutinesApi::class)
    fun toggleFav(id: Int) = GlobalScope.launch { repository.toggleFav(id) }
}
