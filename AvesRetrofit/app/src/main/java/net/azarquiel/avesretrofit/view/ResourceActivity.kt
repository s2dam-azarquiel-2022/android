package net.azarquiel.avesretrofit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import net.azarquiel.avesretrofit.databinding.ActivityResourceBinding
import net.azarquiel.avesretrofit.databinding.CommentDialogBinding
import net.azarquiel.avesretrofit.databinding.CommentRowBinding
import net.azarquiel.avesretrofit.model.CommentData
import net.azarquiel.avesretrofit.model.LiveAdapter
import net.azarquiel.avesretrofit.model.Resource
import net.azarquiel.avesretrofit.viewmodel.MainViewModel

class ResourceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResourceBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var resource: Resource
    private lateinit var comments: MutableLiveData<List<CommentData>>
    private var userID: String? = null

    private fun setup() {
        binding = ActivityResourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        resource = intent.extras?.get("resource")!! as Resource
        userID = intent.getStringExtra("userID")
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        comments = viewModel.getResourceComments(resource.id)

        binding.content.resourceName.text = resource.name
        Picasso.get().load(resource.image).into(binding.content.resourceImg)

        LiveAdapter(
            comments,
            this,
            binding.content.recyclerComments,
            CommentRowBinding::inflate,
        ) { binding, _, item: CommentData ->
            binding.commentDate.text = item.date
            binding.commentAuthor.text = item.author
            binding.commentText.text = item.comment
        }

        if (userID == null) binding.fab.visibility = View.INVISIBLE
        else binding.fab.setOnClickListener {
            val binding = CommentDialogBinding.inflate(LayoutInflater.from(this))
            AlertDialog.Builder(this)
                .setView(binding.root)
                .setTitle("Login | Register")
                .setPositiveButton("Accept") { _, _ ->
                    val comment = binding.commentText.text.toString()
                    viewModel.addResourceComment(resource.id, userID!!, comment).observe(this) { comment ->
                        comments.value = comments.value?.plus(comment) ?: listOf(comment)
                    }
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