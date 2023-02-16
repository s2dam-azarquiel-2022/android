package net.azarquiel.caravanas.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.caravanas.api.MainRepository
import net.azarquiel.caravanas.model.Community

class MainViewModel : ViewModel() {
    private var repository = MainRepository()

    @OptIn(DelicateCoroutinesApi::class)
    fun getCommunities(): MutableLiveData<List<Community>> {
        MutableLiveData<List<Community>>().let {
            GlobalScope.launch(Dispatchers.Main) { it.value = repository.getCommunities() }
            return it
        }
    }
}