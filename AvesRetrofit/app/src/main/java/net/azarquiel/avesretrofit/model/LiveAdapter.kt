package net.azarquiel.avesretrofit.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

@SuppressLint("NotifyDataSetChanged")
class LiveAdapter <B : ViewBinding, I : Any>  (
    data: LiveData<List<I>>,
    context: AppCompatActivity,
    recycler: RecyclerView,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> B,
    private val bind: (B, View, I) -> Unit
) : RecyclerView.Adapter<LiveAdapter.ViewHolder<B>>() {

    private val inflater: LayoutInflater = LayoutInflater.from(recycler.context)
    private var data: List<I> = emptyList()

    init {
        recycler.adapter = this
        recycler.layoutManager = LinearLayoutManager(context)
        data.observe(context) {
            this.data = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> =
        ViewHolder(bindingInflater(inflater, parent, false))

    override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) =
        bind(holder.binding, holder.itemView, data[position])

    override fun getItemCount(): Int = data.size

    class ViewHolder <B : ViewBinding> (
        val binding: B,
    ) : RecyclerView.ViewHolder(binding.root)
}
