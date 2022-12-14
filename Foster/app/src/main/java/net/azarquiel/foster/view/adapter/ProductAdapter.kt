package net.azarquiel.foster.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.foster.R
import net.azarquiel.foster.model.Favorites
import net.azarquiel.foster.model.ProductDetailedView
import net.azarquiel.foster.viemModel.ProductViewModel
import net.azarquiel.foster.view.ProductDetailedActivity

class ProductAdapter(
    private val context: AppCompatActivity,
    thisView: RecyclerView,
    private val itemLayout: Int,
    productViewModel: ProductViewModel,
    categoryID: Int,
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var data: List<ProductDetailedView> = emptyList()
    private var dataCache: List<ProductDetailedView> = emptyList()
    private var showOnlyFavorites: Boolean = false
    private val categoryClickHandler = BrandClickHandler()
    private val activityLaunchHandler = context.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            0 -> (it.data!!.getSerializableExtra("pos") as Int).let { pos ->
                data[pos].favorite = Favorites.getById(data[pos].id!!)
                if (showOnlyFavorites && !data[pos].favorite)
                    setData(data.filter { item -> item.id != data[pos].id })
                else notifyItemChanged(pos)
            }
        }
    }

    init {
        thisView.adapter = this
        thisView.layoutManager = LinearLayoutManager(context)
        productViewModel.getByCategoryID(categoryID).observe(context) { products ->
            products.forEach { it.favorite = Favorites.getById(it.id!!) }
            dataCache = products
            if (showOnlyFavorites) setData(products.filter { it.favorite })
            else setData(products)
        }
    }

    fun toggleShow() {
        showOnlyFavorites = !showOnlyFavorites
        if (showOnlyFavorites) setData(data.filter { it.favorite })
        else setData(dataCache)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ProductDetailedView>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getData(): List<ProductDetailedView> = data

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

        @Suppress("NOTHING_TO_INLINE")
        private inline fun Int.setImage(img: Int) {
            itemView.findViewById<ImageView>(this).setImageResource(img)
        }

        fun bind(item: ProductDetailedView) {
            R.id.productTitle.setText(item.title.checkNull())
            itemView.findViewById<ImageView>(R.id.productImage).let {
                if (item.image.checkNull().isEmpty()) it.setImageResource(android.R.color.transparent)
                else Picasso.get().load(item.image).into(it)
            }
            R.id.productFavorite.setImage(
                if (Favorites.getById(item.id!!)) android.R.drawable.star_on
                else android.R.drawable.star_off
            )

            itemView.tag = adapterPosition
            itemView.setOnClickListener(categoryClickHandler)
        }
    }

    inner class BrandClickHandler : View.OnClickListener {
        override fun onClick(view: View?) {
            (view?.tag as Int).let { pos ->
                Intent(context, ProductDetailedActivity::class.java).let {
                    it.putExtra("product", data[pos])
                    it.putExtra("pos", pos)
                    activityLaunchHandler.launch(it)
                }
            }
        }
    }
}
