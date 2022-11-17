package net.azarquiel.translator.controller.handler

import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import net.azarquiel.translator.controller.adapter.WordAdapter
import net.azarquiel.translator.controller.Dictionary

class SearchWordHandler(
    private val dictionary: Dictionary,
    private val wordAdapter: WordAdapter,
) : SearchView.OnQueryTextListener {
    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextChange(query: String): Boolean {
        wordAdapter.setData(dictionary.langWords[dictionary.currentLangFrom]!!.filter {
            it.value.startsWith(query)
        }.map { it.key })
        wordAdapter.notifyDataSetChanged()
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
}