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
    fun getCommunities(): MutableLiveData<List<Community>> =
        MutableLiveData<List<Community>>().also {
            GlobalScope.launch(Dispatchers.Main) { it.value = repository.getCommunities() }
        }

    @OptIn(DelicateCoroutinesApi::class)
    fun getProvinces(id: String): MutableLiveData<List<Province>> =
        MutableLiveData<List<Province>>().also {
            GlobalScope.launch(Dispatchers.Main) {
                repository.getProvinces(id)?.let { res -> it.value = res }
            }
        }

    @OptIn(DelicateCoroutinesApi::class)
    fun getTowns(id: String): MutableLiveData<List<Town>> =
        MutableLiveData<List<Town>>().also {
            GlobalScope.launch(Dispatchers.Main) {
                repository.getTowns(id)?.let { res -> it.value = res }
            }
        }

    @OptIn(DelicateCoroutinesApi::class)
    fun getParkings(lat: String, lon: String): MutableLiveData<List<Parking>> =
        MutableLiveData<List<Parking>>().also {
            GlobalScope.launch(Dispatchers.Main) {
                repository.getParkings(lat, lon)?.let { res -> it.value = res }
            }
        }

    @OptIn(DelicateCoroutinesApi::class)
    fun getPhotos(id: String): MutableLiveData<List<Photo>> =
        MutableLiveData<List<Photo>>().also {
            GlobalScope.launch(Dispatchers.Main) {
                repository.getPhotos(id)?.let { res -> it.value = res }
            }
        }

    @OptIn(DelicateCoroutinesApi::class)
    fun getAvgRate(id: String): MutableLiveData<Float?> =
        MutableLiveData<Float?>().also {
            GlobalScope.launch(Dispatchers.Main) { it.value = repository.getAvgRate(id) }
        }

    @OptIn(DelicateCoroutinesApi::class)
    fun refreshAvgRate(data: MutableLiveData<Float?>, id: String) =
        GlobalScope.launch(Dispatchers.Main) { data.value = repository.getAvgRate(id) }

    suspend fun login(nick: String, pass: String): UserData? =
        repository.login(nick, pass)

    suspend fun register(nick: String, pass: String): UserData =
        repository.register(nick, pass)

    suspend fun rate(id: String, rate: String): Boolean =
        repository.rate(id, "${Login.userID}", rate)
}