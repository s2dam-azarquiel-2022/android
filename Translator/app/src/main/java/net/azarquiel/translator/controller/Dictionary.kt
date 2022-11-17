package net.azarquiel.translator.controller

import android.content.Context

class Dictionary(
    private val context: Context,
    private val langs: Array<String>,
    var currentLangFrom: String,
    var currentLangTo: String,
) {
    lateinit var langWords: HashMap<String, HashMap<Int, String>>
    private var currentLangFromPos = langs.indexOf(currentLangFrom)
    private var currentLangToPos = langs.indexOf(currentLangTo)

    init {
        getWordsFromLangs()
    }

    private fun getWordsFromLangs() {
        langWords = HashMap(langs.size)
        langs.forEach { lang ->
            langWords[lang] = context.getSharedPreferences(
                lang,
                Context.MODE_PRIVATE
            ).all.let { elems ->
                val result = HashMap<Int, String>(elems.size)
                elems.forEach { elem -> result[elem.key.toInt()] = elem.value.toString() }
                result
            }
        }
    }

    fun nextLangFrom() {
        currentLangFromPos++
        if (currentLangFromPos == langs.size) currentLangFromPos = 0
        currentLangFrom = langs[currentLangFromPos]
    }

    fun nextLangTo() {
        currentLangToPos++
        if (currentLangToPos == langs.size) currentLangToPos = 0
        currentLangTo = langs[currentLangToPos]
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun getWordFrom(id: Int): String = langWords[currentLangFrom]!![id]!!

    @Suppress("NOTHING_TO_INLINE")
    inline fun getWordTo(id: Int): String = langWords[currentLangTo]!![id]!!
}