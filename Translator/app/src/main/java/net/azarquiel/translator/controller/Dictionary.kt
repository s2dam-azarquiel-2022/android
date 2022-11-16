package net.azarquiel.translator.controller

import android.content.Context

class Dictionary(
    private val context: Context,
    var currentLangFrom: String,
    var currentLangTo: String,
) {
    lateinit var langWords: Array<HashMap<Int, String>>
    val langs = arrayOf("es", "en")
    var currentLangFromPos = langs.indexOf(currentLangFrom)
    var currentLangToPos = langs.indexOf(currentLangTo)

    init {
        getWordsFromLangs()
    }

    private fun getWordsFromLangs() {
        langWords = Array(langs.size) { lang ->
            context.getSharedPreferences(
                langs[lang],
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
}