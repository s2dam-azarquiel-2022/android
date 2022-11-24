package net.azarquiel.friendroom.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.friendroom.R
import net.azarquiel.friendroom.databinding.ActivityMainBinding
import net.azarquiel.friendroom.model.AddGroupBtnHandler
import net.azarquiel.friendroom.view.adapter.GroupAdapter
import net.azarquiel.friendroom.viewModel.GroupViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var groupViewModel: GroupViewModel

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        groupAdapter = GroupAdapter(this, binding.contentMain.groupRecycler, R.layout.group_row)

        groupViewModel = ViewModelProvider(this)[GroupViewModel::class.java]
        groupViewModel.getAll().observe(this) { groups ->
            groups.let { groupAdapter.setData(it) }
        }

        binding.addGroupBtn.setOnClickListener(AddGroupBtnHandler(this, groupViewModel))
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