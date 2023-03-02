package net.azarquiel.chistes.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.chistes.R
import net.azarquiel.chistes.api.JokesAPI
import net.azarquiel.chistes.databinding.ActivityMainBinding
import net.azarquiel.chistes.databinding.CategoryRowBinding
import net.azarquiel.chistes.model.Category
import net.azarquiel.chistes.model.FilteredLiveAdapter
import net.azarquiel.chistes.model.Login
import net.azarquiel.chistes.viewModel.MainViewModel
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var liveAdapter: FilteredLiveAdapter<CategoryRowBinding, Category>
    private lateinit var login: Login

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val clickHandler = View.OnClickListener { view ->
            (view?.tag as Category).let { category ->
                this.startActivity(Intent(this, JokesActivity::class.java).also {
                    it.putExtra("category", category)
                })
            }
        }

        liveAdapter = FilteredLiveAdapter(
            this,
            binding.content.categories,
            viewModel.getCategories(),
            CategoryRowBinding::inflate,
            { data, query -> data.filter { it.name.contains(query, true) } }
        ) { binding, view, item: Category ->
            binding.categoryName.text = item.name
            Picasso.get().load("${JokesAPI.baseUrl}/img/${item.id}.png").into(binding.categoryImg)
            view.tag = item
            view.setOnClickListener(clickHandler)
        }

        val liveTitle = MutableLiveData("Jokes")
        liveTitle.observe(this) { title = it }
        login = Login(this, viewModel, liveTitle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        (menu.findItem(R.id.search).actionView as SearchView).let {
            it.queryHint = "Search ..."
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(q: String?): Boolean {
                    liveAdapter.query.value = q
                    return false
                }
                override fun onQueryTextSubmit(q: String?): Boolean = false
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.login -> { login.login(); true }
            R.id.logout -> { login.logout(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
