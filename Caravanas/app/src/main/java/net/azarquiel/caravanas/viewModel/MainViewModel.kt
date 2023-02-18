package net.azarquiel.caravanas.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.azarquiel.caravanas.api.MainRepository
import net.azarquiel.caravanas.model.*

class MainViewModel : ViewModel() {
    private var repository = MainRepository()

    fun getCommunities(): MutableLiveData<List<Community>> =
        ZBg.get { it.value = repository.getCommunities() }

    fun getProvinces(id: String): MutableLiveData<List<Province>> =
        ZBg.get { repository.getProvinces(id)?.let { res -> it.value = res } }

    fun getTowns(id: String): MutableLiveData<List<Town>> =
        ZBg.get { repository.getTowns(id)?.let { res -> it.value = res } }

    fun getParkings(lat: String, lon: String): MutableLiveData<List<Parking>> =
        ZBg.get { repository.getParkings(lat, lon)?.let { res -> it.value = res } }

    fun getPhotos(id: String): MutableLiveData<List<Photo>> =
        ZBg.get { repository.getPhotos(id)?.let { res -> it.value = res } }

    fun getAvgRate(id: String): MutableLiveData<Float?> =
        ZBg.get { it.value = repository.getAvgRate(id) }

    fun refreshAvgRate(data: MutableLiveData<Float?>, id: String) =
        ZBg.run { data.value = repository.getAvgRate(id) }

    suspend fun login(nick: String, pass: String): UserData? =
        repository.login(nick, pass)

    suspend fun register(nick: String, pass: String): UserData? =
        repository.register(nick, pass)

    suspend fun rate(id: String, rate: String): Boolean =
        repository.rate(id, "${Login.userID}", rate)
}