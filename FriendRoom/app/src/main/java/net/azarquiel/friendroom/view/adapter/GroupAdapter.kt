package net.azarquiel.friendroom.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.friendroom.R
import net.azarquiel.friendroom.model.Group

class GroupAdapter(
    context: Context,
    thisView: RecyclerView,
    private val itemLayout: Int,
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private var data: List<Group> = emptyList()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Group>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(itemLayout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos])
    override fun getItemCount(): Int = data.size

    inner class ViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(text: String) {
            itemView.findViewById<TextView>(this).text = text
        }

        fun bind(item: Group) {
            (itemView as CardView).setCardBackgroundColor(item.color)
            R.id.groupName.setText(item.name)
            R.id.groupEmail.setText(item.email)

            itemView.tag = item
        }
    }
}