package net.azarquiel.friendroom.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.friendroom.R
import net.azarquiel.friendroom.databinding.ActivityMainBinding
import net.azarquiel.friendroom.view.adapter.FriendAdapter
import net.azarquiel.friendroom.viewModel.FriendViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var friendAdapter: FriendAdapter
    private lateinit var friendViewModel: FriendViewModel

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        friendAdapter = FriendAdapter(this, binding.contentMain.friendRecycler, R.layout.friend_row)

        friendViewModel = ViewModelProvider(this)[FriendViewModel::class.java]
        friendViewModel.getAll().observe(this) { friends ->
            friends.let { friendAdapter.setData(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }
}