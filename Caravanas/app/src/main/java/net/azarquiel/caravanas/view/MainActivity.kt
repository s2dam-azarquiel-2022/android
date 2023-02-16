package net.azarquiel.caravanas.view

import android.os.Bundle
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

        LiveAdapter(
            ViewModelProvider(this)[MainViewModel::class.java].getCommunities(),
            this,
            binding.content.recyclerCommunities,
            CommunityRowBinding::inflate,
        ) { binding, _, item: Community ->
            binding.communityName.text = item.name
            binding.communityImage.setDrawable("ccaa${item.id}")
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