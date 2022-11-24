package net.azarquiel.friendroom.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.friendroom.databinding.ActivityFriendBinding

class FriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendBinding

    private fun setup() {
        binding = ActivityFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}