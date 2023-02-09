package net.azarquiel.avesretrofit.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.avesretrofit.databinding.ActivityResourceBinding
import net.azarquiel.avesretrofit.databinding.CommentRowBinding
import net.azarquiel.avesretrofit.model.CommentData
import net.azarquiel.avesretrofit.model.LiveAdapter
import net.azarquiel.avesretrofit.model.Resource
import net.azarquiel.avesretrofit.viewmodel.MainViewModel

class ResourceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResourceBinding
    private lateinit var resource: Resource

    private fun setup() {
        binding = ActivityResourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        resource = intent.extras?.get("resource")!! as Resource

        binding.content.resourceName.text = resource.name
        Picasso.get().load(resource.image).into(binding.content.resourceImg)

        LiveAdapter(
            ViewModelProvider(this)[MainViewModel::class.java].getResourceComments(resource.id),
            this,
            binding.content.recyclerComments,
            CommentRowBinding::inflate,
        ) { binding, _, item: CommentData ->
            binding.commentDate.text = item.date
            binding.commentAuthor.text = item.author
            binding.commentText.text = item.comment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }
}