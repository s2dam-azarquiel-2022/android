package net.azarquiel.chistes.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.azarquiel.chistes.api.JokesRepository
import net.azarquiel.chistes.model.*

class MainViewModel : ViewModel() {
    fun getCategories(): MutableLiveData<List<Category>> =
        ZBg.get { it.value = JokesRepository.getCategories() }

    fun getJokes(id: String): MutableLiveData<MutableList<Joke>> =
        ZBg.get { it.value = JokesRepository.getJokes(id) }

    fun addJoke(liveData: MutableLiveData<MutableList<Joke>>, id: String, text: String) =
        ZBg.run { JokesRepository.addJoke(id, text)?.let { liveData += it } }

    fun getAvg(id: String): MutableLiveData<Int> =
        ZBg.get { it.value = JokesRepository.getAvg(id) }

    fun getAvg(liveData: MutableLiveData<Int>, id: String) =
        ZBg.run { liveData.value = JokesRepository.getAvg(id) }

    suspend fun rate(id: String, rating: Int): Rating? =
        JokesRepository.rate(id, rating)

    operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(value: T) {
        this.value = this.value.also { it!!.add(value) }
    }

    suspend fun login(nick: String, pass: String): User? =
        JokesRepository.login(nick, pass)

    suspend fun register(nick: String, pass: String): User? =
        JokesRepository.register(nick, pass)
}
