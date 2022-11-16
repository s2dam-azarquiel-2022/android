package net.azarquiel.translator.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.translator.R
import net.azarquiel.translator.controller.Dictionary
import net.azarquiel.translator.controller.LangSwitch

class WordAdapter(
    context: Context,
    thisView: RecyclerView,
    private val itemLayout: Int,
    private val dictionary: Dictionary,
    private val langSwitch: LangSwitch
) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private var data: List<Int> = emptyList()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        setData(dictionary.langWords[langSwitch.currentLangFromPos].map { it.key })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Int>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos])

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        layout: View,
    ) : RecyclerView.ViewHolder(layout) {
//        @Suppress("NOTHING_TO_INLINE")
//        private inline fun Int.setText(stringId: Int, vararg: Any) {
//            itemView.findViewById<TextView>(this).text = context.getString(stringId, vararg)
//        }

        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(text: String) {
            itemView.findViewById<TextView>(this).text = text
        }

        fun bind(item: Int) {
            R.id.wordFrom.setText(dictionary.langWords[langSwitch.currentLangFromPos][item]!!)
            R.id.wordTo.setText(dictionary.langWords[langSwitch.currentLangToPos][item]!!)

            itemView.tag = item
        }
    }
}