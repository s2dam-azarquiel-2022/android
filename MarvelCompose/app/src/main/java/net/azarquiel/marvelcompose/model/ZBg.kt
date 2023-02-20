package net.azarquiel.marvelcompose.model

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object ZBg {
    @OptIn(DelicateCoroutinesApi::class)
    inline fun run(crossinline f: suspend () -> Unit) =
        GlobalScope.launch(Dispatchers.Main) { f() }

    inline fun <T> get(crossinline f: suspend (MutableLiveData<T>) -> Unit): MutableLiveData<T> =
        MutableLiveData<T>().also { run { f(it) } }
}
