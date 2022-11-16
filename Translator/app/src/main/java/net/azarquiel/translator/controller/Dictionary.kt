package net.azarquiel.translator.controller

import android.content.Context
import net.azarquiel.translator.model.Word

class Dictionary(
    private val context: Context,
    private val langSwitch: LangSwitch,
) {
    lateinit var langWords: Array<Array<Word?>>

    init {
        getWordsFromLangs()
    }

    private fun getWordsFromLangs() {
        langWords = Array(langSwitch.langs.size) { lang ->
            context.getSharedPreferences(langSwitch.langs[lang], Context.MODE_PRIVATE).all.let { w ->
                arrayOfNulls<Word>(w.size).also { arr ->
                    w.forEach { arr[it.key.toInt() - 1] = Word(it.key.toInt() - 1, it.value.toString()) }
                }
            }
        }
    }

//    private fun getWordsFromLangs() {
//        langWords = Array(langSwitch.langs.size) {
//            context.getSharedPreferences(langSwitch.langs[it], Context.MODE_PRIVATE).all.map { w ->
//                Word(w.key.toInt(), w.value.toString())
//            }
//        }
//    }
}