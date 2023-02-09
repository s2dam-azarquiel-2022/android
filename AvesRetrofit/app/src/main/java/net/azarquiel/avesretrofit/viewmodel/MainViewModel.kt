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
import net.azarquiel.avesretrofit.model.CommentData
import net.azarquiel.avesretrofit.model.UserData
import java.text.SimpleDateFormat
import java.util.*

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

    @OptIn(DelicateCoroutinesApi::class)
    fun getResourceComments(id: String?): MutableLiveData<List<CommentData>> {
        MutableLiveData<List<CommentData>>().let {
            id?.let { id -> GlobalScope.launch(Dispatchers.Main) {
                repository.getResourceComments(id)?.let { res -> it.value = res } }
            }
            return it
        }
    }

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

    @OptIn(DelicateCoroutinesApi::class)
    fun addResourceComment(
        id: String,
        userID: String,
        comment: String,
    ): MutableLiveData<CommentData> {
        MutableLiveData<CommentData>().let {
            GlobalScope.launch(Dispatchers.Main) {
                it.value = repository.addResourceComment(
                    id,
                    userID,
                    comment,
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()),
                )
            }
            return it
        }
    }
}