package net.azarquiel.caravanas.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.caravanas.databinding.ActivityMainBinding
import net.azarquiel.caravanas.databinding.CommunityRowBinding
import net.azarquiel.caravanas.model.Community
import net.azarquiel.caravanas.model.LiveAdapter
import net.azarquiel.caravanas.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

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
            ViewModelProvider(this)[MainViewModel::class.java].getCommunities(),
            this,
            binding.content.recyclerCommunities,
            CommunityRowBinding::inflate,
        ) { binding, view, item: Community ->
            binding.communityName.text = item.name
            binding.communityImage.setDrawable("ccaa${item.id}")
            view.tag = item
            view.setOnClickListener(clickHandler)
        }
    }

    private fun ImageView.setDrawable(name: String) {
        this.setImageResource(resources.getIdentifier(name, "drawable", packageName))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}