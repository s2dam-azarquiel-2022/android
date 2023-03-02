package net.azarquiel.chistes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.chistes.api.JokesAPI
import net.azarquiel.chistes.databinding.ActivityJokeBinding
import net.azarquiel.chistes.model.Category
import net.azarquiel.chistes.model.Joke
import net.azarquiel.chistes.viewModel.MainViewModel

class JokeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJokeBinding
    private lateinit var category: Category
    private lateinit var joke: Joke
    private lateinit var viewModel: MainViewModel
    private lateinit var liveAvg: MutableLiveData<Int>

    private fun setup() {
        binding = ActivityJokeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        category = intent.getSerializableExtra("category")!! as Category
        @Suppress("DEPRECATION")
        joke = intent.getSerializableExtra("joke")!! as Joke

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        liveAvg = viewModel.getAvg(joke.id)

        Picasso.get().load("${JokesAPI.baseUrl}/img/${category.id}.png").into(binding.categoryImg)
        binding.categoryName.text = category.name
        binding.joke.text = HtmlCompat.fromHtml(joke.text, 0)

        liveAvg.observe(this) { Log.d("aru", "$it"); binding.avgRate.rating = it.toFloat() }

        binding.rate.setOnRatingBarChangeListener { _, rating, _ ->
            @OptIn(DelicateCoroutinesApi::class)
            GlobalScope.launch {
                val rating = viewModel.rate(joke.id, rating.toInt())
                launch(Dispatchers.Main) {
                    rating?.let { viewModel.getAvg(liveAvg, joke.id) }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}
