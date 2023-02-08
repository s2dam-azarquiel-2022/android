package net.azarquiel.avesretrofit.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.avesretrofit.databinding.ActivityZoneBinding
import net.azarquiel.avesretrofit.model.Zone

class ZoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityZoneBinding
    private lateinit var zone: Zone

    private fun setup() {
        binding = ActivityZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        zone = intent.extras?.get("zone")!! as Zone

        binding.content.zoneName.text = zone.name
        binding.content.zoneLocalization.text = zone.localization
        binding.content.zoneFormations.text = zone.formations
        binding.content.zonePresentation.text = zone.presentation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}