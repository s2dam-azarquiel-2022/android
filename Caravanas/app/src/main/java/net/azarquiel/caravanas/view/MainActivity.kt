package net.azarquiel.caravanas.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.caravanas.R
import net.azarquiel.caravanas.databinding.ActivityMainBinding
import net.azarquiel.caravanas.databinding.CommunityRowBinding
import net.azarquiel.caravanas.model.Community
import net.azarquiel.caravanas.model.LiveAdapter
import net.azarquiel.caravanas.model.Login
import net.azarquiel.caravanas.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var login: Login

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        title = "Comunidades"

        val clickHandler = View.OnClickListener {
            (it?.tag as Community).let { community ->
                Intent(this, ProvincesActivity::class.java).let { intent ->
                    intent.putExtra("community", community)
                    this.startActivity(intent)
                }
            }
        }

        LiveAdapter(
            this,
            binding.content.recyclerCommunities,
            viewModel.getCommunities(),
            CommunityRowBinding::inflate,
        ) { binding, view, item: Community ->
            binding.communityName.text = item.name
            binding.communityImage.setDrawable("ccaa${item.id}")
            view.tag = item
            view.setOnClickListener(clickHandler)
        }

        login = Login(this, viewModel)
    }

    private fun ImageView.setDrawable(name: String) {
        this.setImageResource(resources.getIdentifier(name, "drawable", packageName))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login -> { login.login(); true }
            R.id.logout -> { login.logout(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}