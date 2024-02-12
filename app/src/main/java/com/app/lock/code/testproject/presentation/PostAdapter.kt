package com.app.lock.code.testproject.presentation


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.lock.code.testproject.data.model.Post
import com.app.lock.code.testproject.databinding.ItemPostBinding

class PostAdapter(
    private val deletePost: (Post, Int) -> Unit,
    private val showPost: (Post, Int) -> Unit,
) : ListAdapter<Post, PostAdapter.PostViewHolder>(
    DiffCallBack()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holderPaging: PostViewHolder, position: Int) {

        val currentItem = getItem(position)
        if (currentItem != null)
            holderPaging.bind(currentItem)
    }

    inner class PostViewHolder(private val itemBinding: ItemPostBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.delete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    deletePost(item, position)
                }
            }
            itemBinding.edit.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    showPost(item, position)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Post) {
            itemBinding.apply {
                txtTitle.text = item.title
                txtBody.text = item.body
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(
            oldItem: Post,
            newItem: Post,
        ): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Post,
            newItem: Post,
        ): Boolean =
            oldItem == newItem
    }
}