package net.azarquiel.translator.controller

import android.content.Context

class Dictionary(
    private val context: Context,
    private val langSwitch: LangSwitch,
) {
    lateinit var langWords: Array<HashMap<Int, String>>

    init {
        getWordsFromLangs()
    }

    private fun getWordsFromLangs() {
        langWords = Array(langSwitch.langs.size) { lang ->
            context.getSharedPreferences(
                langSwitch.langs[lang],
                Context.MODE_PRIVATE
            ).all.let { elems ->
                val result = HashMap<Int, String>(elems.size)
                elems.forEach { elem -> result[elem.key.toInt()] = elem.value.toString() }
                result
            }
        }
    }
}