package com.app.lock.code.testproject.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.lock.code.testproject.data.model.Post
import com.app.lock.code.testproject.databinding.ActivityMainBinding
import com.app.lock.code.testproject.databinding.DialogUpdatePostBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            viewModel.loadPost()
        }

        val postAdapter = PostAdapter(
            deletePost = viewModel::deletePost,
            showPost = ::updateDialog
        )
        binding.rv.adapter = postAdapter

        viewModel.state.onEach {
            postAdapter.submitList(it.posts)
        }.launchIn(lifecycleScope)

        binding.btnTask.setOnClickListener {
            startActivity(Intent(this, TaskActivity::class.java))
        }

    }


    private fun updateDialog(post: Post, index: Int) {
        val bindingDialog = DialogUpdatePostBinding.inflate(LayoutInflater.from(this))
        val dialog = MaterialAlertDialogBuilder(this).setView(bindingDialog.root)
            .create()
        bindingDialog.txtTitle.setText(post.title)

        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.update.setOnClickListener {
            viewModel.updatePost(
                post.copy(
                    title = bindingDialog.txtTitle.text.toString(),
                )
            )
            dialog.dismiss()
        }

        dialog.show()

    }
}