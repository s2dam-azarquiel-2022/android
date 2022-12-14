package net.azarquiel.foster.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.foster.R
import net.azarquiel.foster.databinding.ActivityMainBinding
import net.azarquiel.foster.model.DBFiles
import net.azarquiel.foster.model.Favorites
import net.azarquiel.foster.viemModel.CategoryViewModel
import net.azarquiel.foster.view.adapter.CategoryAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        DBFiles.inject(this, "db.sqlite")
        Favorites.setSharedPreferences(getSharedPreferences("favorites", Context.MODE_PRIVATE))

        CategoryAdapter(
            this,
            binding.content.categoryRecycler,
            R.layout.category_row,
            ViewModelProvider(this)[CategoryViewModel::class.java]
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }
}