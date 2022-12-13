package net.azarquiel.alltricks.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.alltricks.R
import net.azarquiel.alltricks.model.BikeListView
import net.azarquiel.alltricks.model.Brand
import net.azarquiel.alltricks.view.BikeDetailedActivity
import net.azarquiel.alltricks.view.BikeListActivity
import net.azarquiel.alltricks.viewModel.BikeViewModel

class BikeAdapter(
    private val context: BikeListActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    bikeViewModel: BikeViewModel,
    brand: Brand
) : RecyclerView.Adapter<BikeAdapter.ViewHolder>() {
    private var data: List<BikeListView> = emptyList()
    private val bikeClickHandler = BikeClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        bikeViewModel.getByBrandId(brand.id).observe(context) { bikes ->
            this.setData(bikes)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<BikeListView>) {
        this.data = data
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
        private inline fun Int.setImage(img: Int) {
            itemView.findViewById<ImageView>(this).setImageResource(img)
        }

        fun bind(item: BikeListView) {
            R.id.bikeName.setText(item.description)
            R.id.bikeStar.setImage(when (item.favorite) {
                1 -> android.R.drawable.star_on
                else -> android.R.drawable.star_off
            })
            Picasso.get().load(item.photo).into(itemView.findViewById<ImageView>(R.id.bikePhoto))

            itemView.tag = item
            itemView.setOnClickListener(bikeClickHandler)
        }
    }

    inner class BikeClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as BikeListView).let { bike ->
                Intent(context, BikeDetailedActivity::class.java).let {
                    it.putExtra("bikeID", bike.id)
                    context.startActivity(it)
                }
            }
        }
    }
}
