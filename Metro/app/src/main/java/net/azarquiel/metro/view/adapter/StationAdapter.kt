package net.azarquiel.metro.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.metro.R
import net.azarquiel.metro.model.LineView
import net.azarquiel.metro.model.StationView
import net.azarquiel.metro.view.LineActivity
import net.azarquiel.metro.viewModel.StationViewModel

class StationAdapter(
    private val context: LineActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    stationViewModel: StationViewModel,
    lineView: LineView
) : RecyclerView.Adapter<StationAdapter.ViewHolder>() {
    private var data: List<StationView> = emptyList()
    private var color: Int = Color.WHITE

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        stationViewModel.getByLine(lineView.id).observe(context) { station ->
            this.setData(station)
        }
        color = Color.parseColor(lineView.color)
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
            (itemView as CardView).setCardBackgroundColor(color)
            R.id.stationName.setText(item.name)
            val images: LinearLayout = itemView.findViewById(R.id.lineImages)
            images.removeAllViews()
            item.lines.forEach { line -> images.addView(ImageView(context).also { image ->
                image.setImage("icon_${line}")
            }) }
            itemView.tag = item
        }
    }
}
