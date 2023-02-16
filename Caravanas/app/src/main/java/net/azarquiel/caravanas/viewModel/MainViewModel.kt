package net.azarquiel.caravanas.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.caravanas.api.MainRepository
import net.azarquiel.caravanas.model.Community
import net.azarquiel.caravanas.model.Province
import net.azarquiel.caravanas.model.Provinces
import net.azarquiel.caravanas.model.Town

class MainViewModel : ViewModel() {
    private var repository = MainRepository()

    @OptIn(DelicateCoroutinesApi::class)
    fun getCommunities(): MutableLiveData<List<Community>> {
        MutableLiveData<List<Community>>().let {
            GlobalScope.launch(Dispatchers.Main) { it.value = repository.getCommunities() }
            return it
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getProvinces(id: String): MutableLiveData<List<Province>> {
        MutableLiveData<List<Province>>().let {
            GlobalScope.launch(Dispatchers.Main) {
                repository.getProvinces(id)?.let { res -> it.value = res }
            }
            return it
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getTowns(id: String): MutableLiveData<List<Town>> {
        MutableLiveData<List<Town>>().let {
            GlobalScope.launch(Dispatchers.Main) {
                repository.getTowns(id)?.let { res -> it.value = res }
            }
            return it
        }
    }
}