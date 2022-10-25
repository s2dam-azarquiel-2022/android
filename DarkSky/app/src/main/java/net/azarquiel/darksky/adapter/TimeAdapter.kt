package net.azarquiel.darksky.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.darksky.R
import java.util.*

abstract class TimeAdapter<T : Any>(
    val context: Context,
    val layout: Int,
) : RecyclerView.Adapter<TimeAdapter.ViewHolder<T>>() {

    abstract var data: List<T>

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T>

    override fun onBindViewHolder(holder: ViewHolder<T>, pos: Int) = holder.bind(data[pos], pos)

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDays(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    abstract class ViewHolder<T>(
        val context: Context,
        view: View,
    ) : RecyclerView.ViewHolder(view) {

        @Suppress("NOTHING_TO_INLINE")
        inline fun Int.setText(stringId: Int, vararg: Any) {
            itemView.findViewById<TextView>(this).text = context.getString(stringId, vararg)
        }

        @Suppress("NOTHING_TO_INLINE")
        inline fun Int.toDayOfWeekName(): String =
            context.resources.getStringArray(R.array.daysOfTheWeek)[this - 1]

        abstract fun bind(item: T, pos: Int)
    }
}