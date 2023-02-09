package net.azarquiel.avesretrofit.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.avesretrofit.R
import net.azarquiel.avesretrofit.databinding.ActivityMainBinding
import net.azarquiel.avesretrofit.databinding.ZoneRowBinding
import net.azarquiel.avesretrofit.model.LiveAdapter
import net.azarquiel.avesretrofit.model.Zone
import net.azarquiel.avesretrofit.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val clickHandler = View.OnClickListener {
            (it?.tag as Zone).let { zone ->
                Intent(this, ZoneActivity::class.java).let { intent ->
                    intent.putExtra("zone", zone)
                    this.startActivity(intent)
                }
            }
        }

        LiveAdapter(
            ViewModelProvider(this)[MainViewModel::class.java].getZones(),
            this,
            binding.content.recyclerZones,
            ZoneRowBinding::inflate,
        ) { binding, view, item: Zone ->
            binding.name.text = item.name
            binding.localization.text = item.localization
            view.tag = item
            view.setOnClickListener(clickHandler)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login -> {
                true
            }
            R.id.logout -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}