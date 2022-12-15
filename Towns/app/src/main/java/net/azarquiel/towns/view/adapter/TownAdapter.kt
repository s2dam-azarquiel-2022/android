package net.azarquiel.towns.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.towns.R
import net.azarquiel.towns.model.TownView
import net.azarquiel.towns.view.TownDetailedActivity

class TownAdapter(
    private val context: AppCompatActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
) : RecyclerView.Adapter<TownAdapter.ViewHolder>() {
    private var data: List<TownView> = emptyList()
    private val townClickHandler = TownClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<TownView>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(itemLayout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos])
    override fun getItemCount(): Int = data.size

    inner class ViewHolder(layout: View) : RecyclerView.ViewHolder(layout) {
        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(text: CharSequence) {
            itemView.findViewById<TextView>(this).text = text
        }

        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setImage(img: Int) {
            itemView.findViewById<ImageView>(this).setImageResource(img)
        }

        fun bind(item: TownView) {
            R.id.townName.setText(item.name)
            R.id.townProvince.setText(item.province)
            R.id.townStar.setImage(when (item.favorite) {
                1 ->  android.R.drawable.star_on
                else -> android.R.drawable.star_off
            })
            Picasso.get().load(item.image).into(itemView.findViewById<ImageView>(R.id.townImage))

            itemView.tag = item
            itemView.setOnClickListener(townClickHandler)
        }
    }

    inner class TownClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as TownView).let { town ->
                Intent(context, TownDetailedActivity::class.java).let {
                    it.putExtra("town", town)
                    context.startActivity(it)
                }
            }
        }
    }
}
