package net.azarquiel.chistes.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class ListAdapter <B : ViewBinding, I : Any>  (
    private val context: AppCompatActivity,
    private val recycler: RecyclerView,
    open var data: List<I>,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> B,
    private val bind: (B, View, I) -> Unit,
) : RecyclerView.Adapter<ListAdapter.ViewHolder<B>>() {
    private val inflater: LayoutInflater = LayoutInflater.from(recycler.context)

    fun init(): ListAdapter<B, I> {
        recycler.adapter = this
        recycler.layoutManager = LinearLayoutManager(context)
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> =
        ViewHolder(bindingInflater(inflater, parent, false))

    override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) =
        bind(holder.binding, holder.itemView, data[position])

    override fun getItemCount(): Int = data.size

    class ViewHolder <B : ViewBinding> (val binding: B ) : RecyclerView.ViewHolder(binding.root)
}

@SuppressLint("NotifyDataSetChanged")
class LiveAdapter <B : ViewBinding, I : Any> (
    context: AppCompatActivity,
    recycler: RecyclerView,
    liveData: LiveData<List<I>>,
    bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> B,
    bind: (B, View, I) -> Unit,
) : ListAdapter<B, I>(
    context,
    recycler,
    emptyList(),
    bindingInflater,
    bind,
) {
    init {
        super.init()
        liveData.observe(context) {
            this.data = it
            notifyDataSetChanged()
        }
    }
}

@SuppressLint("NotifyDataSetChanged")
class FilteredLiveAdapter <B : ViewBinding, I : Any> (
    context: AppCompatActivity,
    recycler: RecyclerView,
    data: LiveData<List<I>>,
    bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> B,
    filter: (List<I>, String) -> List<I>,
    bind: (B, View, I) -> Unit,
) : ListAdapter<B, I>(
    context,
    recycler,
    emptyList(),
    bindingInflater,
    bind,
) {
    val query = MutableLiveData("")

    init {
        super.init()
        data.observe(context) {
            if (query.value!!.isEmpty()) this.data = it
            else this.data = filter(it, query.value!!)
            notifyDataSetChanged()
        }
        query.observe(context) { query -> data.value?.let {
            this.data = filter(it, query)
            notifyDataSetChanged()
        } }
    }
}
