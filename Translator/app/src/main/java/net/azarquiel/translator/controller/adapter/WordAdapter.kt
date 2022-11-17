package net.azarquiel.translator.controller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.translator.R
import net.azarquiel.translator.controller.Dictionary
import java.util.*

class WordAdapter(
    context: Context,
    thisView: RecyclerView,
    private val itemLayout: Int,
    private val dictionary: Dictionary,
    private val tts: TextToSpeech
) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private lateinit var data: List<Int>
    private var clickHandler: ClickHandler

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)

        setData(dictionary.langWords[dictionary.currentLangFrom]!!.map { it.key })
        clickHandler = ClickHandler()
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
            R.id.wordFrom.setText(dictionary.getWordFrom(item))
            R.id.wordTo.setText(dictionary.getWordTo(item))

            itemView.tag = item
            itemView.setOnClickListener(clickHandler)
        }
    }

    inner class ClickHandler : View.OnClickListener {
        private fun getLocale(lang: String): Locale {
            return when (lang) {
                "en" -> Locale.US
                else -> Locale(lang, lang.uppercase())
            }
        }
        override fun onClick(v: View?) {
            val wordId = v!!.tag as Int
            tts.language = getLocale(dictionary.currentLangFrom)
            tts.speak(
                dictionary.langWords[dictionary.currentLangFrom]!![wordId]!!,
                TextToSpeech.QUEUE_ADD, null, ""
            )

            tts.language = getLocale(dictionary.currentLangTo)
            tts.speak(
                dictionary.langWords[dictionary.currentLangTo]!![wordId]!!,
                TextToSpeech.QUEUE_ADD, null, ""
            )
        }
    }
}