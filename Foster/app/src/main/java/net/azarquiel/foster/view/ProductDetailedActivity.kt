package net.azarquiel.foster.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.foster.databinding.ActivityProductDetailedBinding
import net.azarquiel.foster.model.Favorites
import net.azarquiel.foster.model.ProductDetailedView
import net.azarquiel.foster.viemModel.ProductViewModel

class ProductDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailedBinding
    private lateinit var product: ProductDetailedView
    private var favorite: Boolean = false

    @Suppress("NOTHING_TO_INLINE")
    private inline fun String?.checkNull(): String {
        return this.let { if (it == "null") "" else it } ?: ""
    }

    private fun setup() {
        binding = ActivityProductDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        product = intent.getSerializableExtra("product") as ProductDetailedView

        binding.content.productTitle.text = product.title.checkNull()
        binding.content.productBody.text = HtmlCompat.fromHtml(product.body.checkNull(), 0)
        binding.content.productImage.let {
            if (product.image.checkNull().isEmpty()) it.setImageResource(android.R.color.transparent)
            else Picasso.get().load(product.image).into(it)
        }
        binding.content.productSummary.text = HtmlCompat.fromHtml(product.summary.checkNull(), 0)
        favorite = Favorites.getById(product.id!!)
        setFavoriteImage()

        binding.fab.setOnClickListener {
            favorite = !favorite
            setFavoriteImage()
            Favorites.setById(product.id!!, favorite)
        }

        setResult(0, Intent().also {
            it.putExtra("pos", intent.getSerializableExtra("pos") as Int)
        })
    }

    private fun setFavoriteImage() =
        binding.fab.setImageResource(
            if (favorite) android.R.drawable.star_on
            else android.R.drawable.star_off
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}