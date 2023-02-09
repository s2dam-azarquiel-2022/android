package net.azarquiel.avesretrofit.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.avesretrofit.databinding.ActivityResourcesBinding
import net.azarquiel.avesretrofit.databinding.ResourceRowBinding
import net.azarquiel.avesretrofit.model.LiveAdapter
import net.azarquiel.avesretrofit.model.Resource
import net.azarquiel.avesretrofit.viewmodel.MainViewModel

class ResourcesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResourcesBinding

    private fun setup() {
        binding = ActivityResourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        LiveAdapter(
            ViewModelProvider(this)[MainViewModel::class.java].getZoneResources(intent.getStringExtra("zoneID")),
            this,
            binding.content.recyclerResources,
            ResourceRowBinding::inflate,
        ) { binding, view, item: Resource ->
            binding.resourceName.text = item.name
            Picasso.get().load(item.image).into(binding.resourceImg)
            view.tag = item
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}