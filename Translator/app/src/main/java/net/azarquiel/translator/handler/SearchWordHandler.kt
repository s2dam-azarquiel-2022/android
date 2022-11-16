package net.azarquiel.translator.handler

import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.widget.SearchView
import net.azarquiel.translator.adapter.WordAdapter
import net.azarquiel.translator.controller.Dictionary
import net.azarquiel.translator.controller.LangSwitch

class SearchWordHandler(
    private val langSwitch: LangSwitch,
    private val dictionary: Dictionary,
    private val wordAdapter: WordAdapter,
) : SearchView.OnQueryTextListener {
    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextChange(query: String): Boolean {
        Log.d("aru", query)
        val a = dictionary.langWords[langSwitch.currentLangFromPos].filter {
            it!!.word.startsWith(query)
        }.map { it!!.id }
        wordAdapter.setData(a)
        wordAdapter.notifyDataSetChanged()
        Log.d("aru", a.toString())
        Log.d("aru", wordAdapter.getData().toString())
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
}