package net.azarquiel.retrofittemplate.view.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.retrofittemplate.databinding.LoginDialogBinding
import net.azarquiel.retrofittemplate.viewModel.MainViewModel

class Login(
    private val context: AppCompatActivity,
    private val viewModel: MainViewModel,
) {
    companion object {
        private lateinit var sp: SharedPreferences
        var userID: String? = null

        private fun spInitialized() = this::sp.isInitialized
    }

    init {
        if (!spInitialized()) {
            sp = context.getSharedPreferences("login", Context.MODE_PRIVATE)
            userID = sp.getString("user", null)
        }
    }

    private fun String.toast() =
        Toast.makeText(context, this, Toast.LENGTH_LONG).show()

    private fun saveUser(userID: String, registered: Boolean = false) {
        Login.userID = userID
        sp.edit(true) { putString("user", userID) }
        "${if (registered) "Registered" else "Logged in"} successfully".toast()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun login() {
        if (userID != null) {
            "Already logged in".toast()
            return
        }
        val binding = LoginDialogBinding.inflate(LayoutInflater.from(context))
        AlertDialog.Builder(context)
            .setView(binding.root)
            .setTitle("Login | Register")
            .setPositiveButton("Accept") { _, _ ->
                val nick = binding.userNick.text.toString()
                val pass = binding.userPassword.text.toString()
                GlobalScope.launch {
                    val res = viewModel.login(nick, pass)
                    launch(Dispatchers.Main) {
                        if (res == null) register(nick, pass)
                        else saveUser(res.id)
                    }
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun register(nick: String, pass: String) {
        GlobalScope.launch {
            val res = viewModel.register(nick, pass)
            launch(Dispatchers.Main) {
                if (res == null) "There was an error registering you".toast()
                else saveUser(res.id, true)
            }
        }
    }

    fun logout() {
        if (userID == null) {
            "You can't log out since you're not logged in".toast()
            return
        }
        AlertDialog.Builder(context)
            .setTitle("Sure to logout?")
            .setPositiveButton("Accept") { _, _ ->
                userID = null
                sp.edit(true) { putString("user", null) }
                "Logged out successfully".toast()
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }
}
