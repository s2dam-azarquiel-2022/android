package net.azarquiel.foster.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.foster.R
import net.azarquiel.foster.model.Category
import net.azarquiel.foster.viemModel.CategoryViewModel
import net.azarquiel.foster.view.ProductListActivity

class CategoryAdapter(
    private val context: AppCompatActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    categoryViewModel: CategoryViewModel
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var data: List<Category> = emptyList()
    private val categoryClickHandler = BrandClickHandler()

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        categoryViewModel.getAll().observe(context) { categories ->
            this.setData(categories)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Category>) {
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
        private inline fun String?.checkNull(): String {
            return this.let { if (it == "null") "" else it } ?: ""
        }

        fun bind(item: Category) {
            R.id.categoryName.setText(item.name.checkNull())
            R.id.categoryDescription.setText(HtmlCompat.fromHtml(item.description.checkNull(), 0))
            R.id.categoryGarrison.setText(HtmlCompat.fromHtml(item.garrison.checkNull(), 0))

            itemView.tag = item.id
            itemView.setOnClickListener(categoryClickHandler)
        }
    }

    inner class BrandClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as Int?).let { categoryID ->
                Intent(context, ProductListActivity::class.java).let {
                    it.putExtra("categoryID", categoryID ?: -1)
                    context.startActivity(it)
                }
            }
        }
    }
}
