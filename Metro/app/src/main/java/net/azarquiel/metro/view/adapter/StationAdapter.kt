package net.azarquiel.metro.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.metro.R
import net.azarquiel.metro.model.StationView
import net.azarquiel.metro.view.LineActivity
import net.azarquiel.metro.viewModel.StationViewModel

class StationAdapter(
    private val context: LineActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    stationViewModel: StationViewModel,
    line: Int
) : RecyclerView.Adapter<StationAdapter.ViewHolder>() {
    private var data: List<StationView> = emptyList()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        stationViewModel.getByLine(line).observe(context) { station ->
            this.setData(station)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: Map<String, List<Int>>) {
        this.data = data.map { StationView(it.key, it.value) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(itemLayout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos])
    override fun getItemCount(): Int = data.size

    inner class ViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(text: String) {
            itemView.findViewById<TextView>(this).text = text
        }

        @Suppress("NOTHING_TO_INLINE")
        private inline fun ImageView.setImage(name: String) {
            this.setImageResource(
                context.resources.getIdentifier(name, "drawable", context.packageName)
            )
        }

        fun bind(item: StationView) {
            val images: LinearLayout = itemView.findViewById(R.id.lineImages)
            R.id.stationName.setText(item.name)
            item.lines.forEach { line -> images.addView(ImageView(context).also { image ->
                image.setImage("icon_${line}")
            }) }
            itemView.tag = item
        }
    }
}
