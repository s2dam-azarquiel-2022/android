package net.azarquiel.darksky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.azarquiel.darksky.R
import net.azarquiel.darksky.dao.DarkSky
import java.util.*

class HourlyAdapter(c: Context, l: Int) : TimeAdapter<DarkSky.TimeHourly>(c, l) {
    override var data: List<DarkSky.TimeHourly> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourView = HourView(
        context,
        LayoutInflater.from(parent.context).inflate(layout, parent, false),
    )

    class HourView(c: Context, v: android.view.View) : ViewHolder<DarkSky.TimeHourly>(c, v) {
        private fun Long.toDayOfWeek(): String = Calendar.getInstance().let {
            it.timeInMillis = this * 1000
            context.getString(
                R.string.hour,
                it.get(Calendar.HOUR),
                it.get(Calendar.MINUTE),
                it.get(Calendar.DAY_OF_MONTH),
                it.get(Calendar.MONTH)
            )
        }

        override fun bind(item: DarkSky.TimeHourly, pos: Int) {
            itemView.findViewById<TextView>(R.id.day).text = item.time.toDayOfWeek()
            R.id.temp.setText(R.string.temp, item.temperature)
            R.id.summary.setText(R.string.summary, item.summary)
            R.id.precipProbability.setText(R.string.precipProbability, item.getPrecipProbability())
            R.id.humidity.setText(R.string.humidity, item.humidity)
            R.id.windSpeed.setText(R.string.windSpeed, item.windSpeed)
            Picasso.get().load(item.getIcon()).into(itemView.findViewById<ImageView>(R.id.icon))
            itemView.tag = item
        }
    }
}