package net.azarquiel.tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.tmdb.adapter.PersonsAdapter
import net.azarquiel.tmdb.dao.TMDB

class MainActivity : AppCompatActivity() {
    private lateinit var data: TMDB.Result
    private lateinit var personsAdapter: PersonsAdapter

    private fun setup() {
        personsAdapter = PersonsAdapter(this@MainActivity, R.layout.person)
        findViewById<RecyclerView>(R.id.persons).let {
            it.adapter = personsAdapter
            it.layoutManager = LinearLayoutManager(this)
        }
        GlobalScope.launch {
            data = TMDB.getPeople()
            Log.d("aru", data.toString())
            launch(Dispatchers.Main) { personsAdapter.setPersons(data.data) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }
}