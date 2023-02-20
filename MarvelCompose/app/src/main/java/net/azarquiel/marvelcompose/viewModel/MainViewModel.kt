package net.azarquiel.marvelcompose.viewModel

import androidx.lifecycle.ViewModel
import net.azarquiel.marvelcompose.api.MainRepository
import net.azarquiel.marvelcompose.model.UserData

class MainViewModel : ViewModel() {
    private var repository = MainRepository()

// Some examples:

//    fun getCommunities(): MutableLiveData<List<Community>> =
//        ZBg.get { it.value = repository.getCommunities() }
//
//    fun getAvgRate(id: String): MutableLiveData<Float?> =
//        ZBg.get { it.value = repository.getAvgRate(id) }
//
//    fun refreshAvgRate(data: MutableLiveData<Float?>, id: String) =
//        ZBg.run { data.value = repository.getAvgRate(id) }

    suspend fun login(nick: String, pass: String): UserData? =
        repository.login(nick, pass)

    suspend fun register(nick: String, pass: String): UserData? =
        repository.register(nick, pass)
}
