package net.azarquiel.towns.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
}
