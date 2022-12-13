package net.azarquiel.alltricks.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.alltricks.R
import net.azarquiel.alltricks.model.Brand
import net.azarquiel.alltricks.view.BikeListActivity
import net.azarquiel.alltricks.view.MainActivity
import net.azarquiel.alltricks.viewModel.BrandViewModel

class BrandAdapter(
    private val context: MainActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    brandViewModel: BrandViewModel
) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {
    private var data: List<Brand> = emptyList()
    private val brandClickHandler = BrandClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        brandViewModel.getAll().observe(context) { lines ->
            this.setData(lines)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Brand>) {
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

        fun bind(item: Brand) {
            R.id.brandName.setText(item.name)

            itemView.tag = item
            itemView.setOnClickListener(brandClickHandler)
        }
    }

    inner class BrandClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as Brand).let { brand ->
                Intent(context, BikeListActivity::class.java).let {
                    it.putExtra("brand", brand)
                    context.startActivity(it)
                }
            }
        }
    }
}
