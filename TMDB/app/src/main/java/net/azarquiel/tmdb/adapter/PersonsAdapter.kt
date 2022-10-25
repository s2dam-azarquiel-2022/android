package net.azarquiel.tmdb.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.tmdb.R
import net.azarquiel.tmdb.dao.TMDB
import java.util.*

class PersonsAdapter(
    private val context: Context,
    private val layout: Int,
) : RecyclerView.Adapter<PersonsAdapter.ViewHolder>() {

    private var data: List<TMDB.Person> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout, parent, false),
        context,
    )

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(data[pos], pos)

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setPersons(data: List<TMDB.Person>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(
        layout: View,
        private val context: Context,
    ) : RecyclerView.ViewHolder(layout) {

        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setText(stringId: Int, vararg: Any) {
            itemView.findViewById<TextView>(this).text = context.getString(stringId, vararg)
        }

        fun bind(item: TMDB.Person, pos: Int) {
            R.id.name.setText(R.string.name, item.name)
            R.id.popularity.setText(R.string.popularity, item.popularity)
            R.id.id.setText(R.string.id, item.id)
            item.getPfp()?.let { Picasso.get().load(it).into(itemView.findViewById<ImageView>(R.id.pfp)) }
        }
    }
}
