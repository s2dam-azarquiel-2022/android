package net.azarquiel.tapitas.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.tapitas.R
import net.azarquiel.tapitas.model.TapaView
import net.azarquiel.tapitas.view.TapaDetailedActivity
import net.azarquiel.tapitas.viewModel.TapaViewModel

class TapaAdapter(
    private val context: AppCompatActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    tapaViewModel: TapaViewModel
) : RecyclerView.Adapter<TapaAdapter.ViewHolder>() {
    private var data: List<TapaView> = emptyList()
    private val communityClickHandler = CommunityClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        tapaViewModel.getAll().observe(context) { tapas ->
            this.setData(tapas)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<TapaView>) {
        Log.d("aru", "reached")
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

        fun bind(item: TapaView) {
            R.id.tapaName.setText(item.name)
            R.id.tapaStablishment.setText(item.stablishment)
            R.id.tapaDescription.setText(item.description)
            Picasso.get().load("http://82.223.108.85/storage/${item.imageURL}").into(
                itemView.findViewById<ImageView>(R.id.tapaImage)
            )

            itemView.tag = item.id
            itemView.setOnClickListener(communityClickHandler)
        }
    }

    inner class CommunityClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as Int).let { tapaID ->
                Intent(context, TapaDetailedActivity::class.java).let {
                    it.putExtra("tapaID", tapaID)
                    context.startActivity(it)
                }
            }
        }
    }
}