package net.azarquiel.caravanas.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.caravanas.api.MainRepository
import net.azarquiel.caravanas.model.*

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

    @OptIn(DelicateCoroutinesApi::class)
    fun getParkings(lat: String, lon: String): MutableLiveData<List<Parking>> {
        MutableLiveData<List<Parking>>().let {
            GlobalScope.launch(Dispatchers.Main) {
                repository.getParkings(lat, lon)?.let { res -> it.value = res }
            }
            return it
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getPhotos(id: String): MutableLiveData<List<Photo>> {
        MutableLiveData<List<Photo>>().let {
            GlobalScope.launch(Dispatchers.Main) {
                repository.getPhotos(id)?.let { res -> it.value = res }
            }
            return it
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getAvgRate(id: String): MutableLiveData<Float?> {
        MutableLiveData<Float?>().let {
            GlobalScope.launch(Dispatchers.Main) { it.value = repository.getAvgRate(id) }
            return it
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun refreshAvgRate(data: MutableLiveData<Float?>, id: String) =
        GlobalScope.launch(Dispatchers.Main) { data.value = repository.getAvgRate(id) }

    @OptIn(DelicateCoroutinesApi::class)
    fun login(nick: String, pass: String): MutableLiveData<UserData?> {
        MutableLiveData<UserData?>().let {
            GlobalScope.launch(Dispatchers.Main) { it.value = repository.login(nick, pass) }
            return it
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun register(nick: String, pass: String): MutableLiveData<UserData> {
        MutableLiveData<UserData>().let {
            GlobalScope.launch(Dispatchers.Main) { it.value = repository.register(nick, pass) }
            return it
        }
    }

    suspend fun rate(id: String, rate: String): Boolean =
        repository.rate(id, "${Login.userID}", rate)
}