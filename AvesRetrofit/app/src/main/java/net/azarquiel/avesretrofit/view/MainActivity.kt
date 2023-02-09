package net.azarquiel.avesretrofit.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.avesretrofit.R
import net.azarquiel.avesretrofit.databinding.ActivityMainBinding
import net.azarquiel.avesretrofit.databinding.LoginDialogBinding
import net.azarquiel.avesretrofit.databinding.ZoneRowBinding
import net.azarquiel.avesretrofit.model.LiveAdapter
import net.azarquiel.avesretrofit.model.Zone
import net.azarquiel.avesretrofit.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var userID: String? = null

    private fun setup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        userID = sharedPreferences.getString("user", null)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val clickHandler = View.OnClickListener {
            (it?.tag as Zone).let { zone ->
                Intent(this, ZoneActivity::class.java).let { intent ->
                    intent.putExtra("zone", zone)
                    this.startActivity(intent)
                }
            }
        }

        LiveAdapter(
            viewModel.getZones(),
            this,
            binding.content.recyclerZones,
            ZoneRowBinding::inflate,
        ) { binding, view, item: Zone ->
            binding.name.text = item.name
            binding.localization.text = item.localization
            view.tag = item
            view.setOnClickListener(clickHandler)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun String.toast() =
        Toast.makeText(this@MainActivity, this, Toast.LENGTH_LONG).show()

    private fun saveUser(userID: String, registered: Boolean = false) {
        this.userID = userID
        sharedPreferences.edit(true) { putString("user", userID) }
        "${if (registered) "Registered" else "Logged in"} succesfully".toast()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login -> {
                if (userID != null) {
                    "Already logged in".toast()
                    return true
                }
                val binding = LoginDialogBinding.inflate(LayoutInflater.from(this))
                AlertDialog.Builder(this)
                    .setView(binding.root)
                    .setTitle("Login | Register")
                    .setPositiveButton("Accept") { _, _ ->
                        val nick = binding.userNick.text.toString()
                        val pass = binding.userPassword.text.toString()
                        viewModel.login(nick, pass).observe(this) {
                            if (it == null) viewModel.register(nick, pass).observe(this) { saveUser(it.id, true) }
                            else saveUser(it.id)
                        }
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
                true
            }
            R.id.logout -> {
                if (userID == null) {
                    "You can't log out since you're not logged in".toast()
                    return true
                }
                AlertDialog.Builder(this)
                    .setTitle("Sure to logout?")
                    .setPositiveButton("Accept") { _, _ ->
                        userID = null
                        sharedPreferences.edit(true) { putString("user", null) }
                        "Logged out successfully".toast()
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}