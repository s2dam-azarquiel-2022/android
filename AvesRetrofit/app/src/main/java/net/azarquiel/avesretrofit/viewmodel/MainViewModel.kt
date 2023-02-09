package net.azarquiel.avesretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.avesretrofit.model.Resource
import net.azarquiel.avesretrofit.model.Zone
import net.azarquiel.avesretrofit.api.MainRepository

class MainViewModel : ViewModel() {
    private var repository = MainRepository()

    @OptIn(DelicateCoroutinesApi::class)
    fun getZones(): MutableLiveData<List<Zone>> {
        MutableLiveData<List<Zone>>().let {
            GlobalScope.launch(Dispatchers.Main) { it.value = repository.getZones() }
            return it
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getZoneResources(id: String?): MutableLiveData<List<Resource>> {
        MutableLiveData<List<Resource>>().let {
            id?.let { id -> GlobalScope.launch(Dispatchers.Main) {
                repository.getZoneResources(id)?.let { res -> it.value = res } }
            }
            return it
        }
    }
}