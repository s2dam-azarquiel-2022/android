package net.azarquiel.metro.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.metro.R
import net.azarquiel.metro.model.LineView
import net.azarquiel.metro.view.LineActivity
import net.azarquiel.metro.view.MainActivity
import net.azarquiel.metro.viewModel.LineViewModel
import net.azarquiel.metro.viewModel.StationViewModel

class LineAdapter(
    private val context: MainActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    lineViewModel: LineViewModel,
) : RecyclerView.Adapter<LineAdapter.ViewHolder>() {
    private var data: List<LineView> = emptyList()
    private val groupClickHandler = GroupClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        lineViewModel.getAll().observe(context) { lines ->
            this.setData(lines)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<LineView>) {
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
        private inline fun Int.setImage(name: String) {
            itemView.findViewById<ImageView>(this).setImageResource(
                context.resources.getIdentifier(name, "drawable", context.packageName)
            )
        }

        fun bind(item: LineView) {
            (itemView as ConstraintLayout).setBackgroundColor(Color.parseColor(item.color))
            R.id.lineName.setText(item.name)
            R.id.lineStartEnd.setText(item.startEnd)
            R.id.lineNumberImage.setImage("icon_${item.id}")

            itemView.tag = item
            itemView.setOnClickListener(groupClickHandler)
        }
    }

    inner class GroupClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as LineView).let { lineView ->
                Intent(context, LineActivity::class.java).let {
                    it.putExtra("lineView", lineView)
                    context.startActivity(it)
                }
            }
        }
    }
}
