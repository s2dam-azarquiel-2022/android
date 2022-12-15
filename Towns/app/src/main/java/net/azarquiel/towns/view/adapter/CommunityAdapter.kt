package net.azarquiel.towns.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.towns.R
import net.azarquiel.towns.model.Community
import net.azarquiel.towns.viewModel.CommunityViewModel

class CommunityAdapter(
    private val context: AppCompatActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    communityViewModel: CommunityViewModel
) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    private var data: List<Community> = emptyList()
    private val communityClickHandler = CommunityClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        communityViewModel.getAll().observe(context) { communities ->
            this.setData(communities)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Community>) {
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

        fun bind(item: Community) {
            R.id.communityName.setText(item.name)

            itemView.tag = item
            itemView.setOnClickListener(communityClickHandler)
        }
    }

    inner class CommunityClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
//            (view?.tag as Community).let { community ->
//                Intent(context, ProductListActivity::class.java).let {
//                    it.putExtra("categoryID", community)
//                    context.startActivity(it)
//                }
//            }
        }
    }
}
