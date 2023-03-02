package net.azarquiel.chistes.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.chistes.api.JokesAPI
import net.azarquiel.chistes.databinding.ActivityJokesBinding
import net.azarquiel.chistes.databinding.JokeRowBinding
import net.azarquiel.chistes.databinding.NewJokeDialogBinding
import net.azarquiel.chistes.model.*
import net.azarquiel.chistes.viewModel.MainViewModel

class JokesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJokesBinding
    private lateinit var category: Category
    private lateinit var viewModel: MainViewModel
    private lateinit var liveData: MutableLiveData<MutableList<Joke>>

    private fun setup() {
        binding = ActivityJokesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        @Suppress("DEPRECATION")
        category = intent.getSerializableExtra("category")!! as Category
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        title = "Chistes de ${category.name}"

        val img = Picasso.get().load("${JokesAPI.baseUrl}/img/${category.id}.png")
        liveData = viewModel.getJokes(category.id)

        val clickHandler = View.OnClickListener { view ->
            (view?.tag as Joke).let { joke ->
                this.startActivity(Intent(this, JokeActivity::class.java).also {
                    it.putExtra("category", category)
                    it.putExtra("joke", joke)
                })
            }
        }

        LiveAdapter(
            this,
            binding.content.jokes,
            liveData as LiveData<List<Joke>>,
            JokeRowBinding::inflate
        ) { binding, view, item: Joke ->
            binding.joke.text = HtmlCompat.fromHtml(item.text.substring(0, minOf(25, item.text.length)), 0)
            img.into(binding.categoryImg)
            view.tag = item
            view.setOnClickListener(clickHandler)
        }

        if (Login.userID == null) binding.fab.visibility = View.GONE
        else binding.fab.setOnClickListener {
            val binding = NewJokeDialogBinding.inflate(LayoutInflater.from(this))
            AlertDialog.Builder(this)
                .setView(binding.root)
                .setTitle("Login | Register")
                .setPositiveButton("Accept") { _, _ ->
                    viewModel.addJoke(liveData, category.id, binding.joke.text.toString())
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}
