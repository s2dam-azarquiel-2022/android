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

class DailyAdapter(c: Context, l: Int) : TimeAdapter<DarkSky.TimeDaily>(c, l) {
    override var data: List<DarkSky.TimeDaily> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView = DayView(
        context,
        LayoutInflater.from(parent.context).inflate(layout, parent, false),
    )

    class DayView(c: Context, v: android.view.View) : ViewHolder<DarkSky.TimeDaily>(c, v) {
        private fun Long.toDayOfWeek(): String = Calendar.getInstance().let {
            it.timeInMillis = this * 1000
            context.resources.getStringArray(R.array.daysOfTheWeek)[it.get(Calendar.DAY_OF_WEEK)-1]
        }

        override fun bind(item: DarkSky.TimeDaily, pos: Int) {
            itemView.findViewById<TextView>(R.id.day).text =
                if (pos == 0) context.getString(R.string.today)
                else item.time.toDayOfWeek()
            R.id.tempMin.setText(R.string.temp, item.temperatureMin)
            R.id.tempMax.setText(R.string.temp, item.temperatureMax)
            R.id.summary.setText(R.string.summary, item.summary)
            R.id.precipProbability.setText(R.string.precipProbability, item.getPrecipProbability())
            R.id.humidity.setText(R.string.humidity, item.humidity)
            R.id.windSpeed.setText(R.string.windSpeed, item.windSpeed)
            Picasso.get().load(item.getIcon()).into(itemView.findViewById<ImageView>(R.id.icon))
            itemView.tag = item
        }
    }
}