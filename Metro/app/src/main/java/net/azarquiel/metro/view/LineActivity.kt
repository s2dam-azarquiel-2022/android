package net.azarquiel.metro.view

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.metro.R
import net.azarquiel.metro.databinding.ActivityLineBinding
import net.azarquiel.metro.model.LineView
import net.azarquiel.metro.view.adapter.StationAdapter
import net.azarquiel.metro.viewModel.StationViewModel

class LineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLineBinding
    private lateinit var stationViewModel: StationViewModel
    private lateinit var lineView: LineView

    private fun setup() {
        binding = ActivityLineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        lineView = intent.getSerializableExtra("lineView") as LineView
        stationViewModel = ViewModelProvider(this)[StationViewModel::class.java]

        binding.contentMain.lineName.text = lineView.name
        binding.contentMain.lineStartEnd.text = lineView.startEnd

        binding.contentMain.lineMainView.setBackgroundColor(Color.parseColor(lineView.color))

        StationAdapter(
            this,
            binding.contentMain.stationsRecycler,
            R.layout.station_row,
            stationViewModel,
            lineView.id
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}