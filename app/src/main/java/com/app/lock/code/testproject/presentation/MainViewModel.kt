package com.app.lock.code.testproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.app.lock.code.testproject.data.api.APIService
import com.app.lock.code.testproject.data.db.AppDatabase
import com.app.lock.code.testproject.data.model.Post
import com.app.lock.code.testproject.data.db.PostDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: APIService,
    private val postDao: PostDao,
    private val appDatabase: AppDatabase,
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()


    init {
        observerPosts()
        loadPost()
    }

    private fun observerPosts() {
        postDao.getPostsAsync().flowOn(IO).onEach {posts->
            _state.update { it.copy(posts = posts) }
        }.launchIn(viewModelScope)
    }

    fun loadPost() {
        viewModelScope.launch(IO) {
            appDatabase.withTransaction {
                try {
                    _state.update { it.copy(loading = true) }
                    val postsArray = apiService.getPosts()
                    val posts = Gson().fromJson(postsArray, Array<Post>::class.java)
                    postDao.deleteAllPosts()
                    postDao.insertPosts(posts.toList())
                    _state.update { it.copy(loading = false, posts = postDao.getPosts()) }
                } catch (e: Exception) {
                    val posts = postDao.getPosts()
                    _state.update {
                        it.copy(
                            posts = posts, loading = false, error = e.localizedMessage.orEmpty()
                        )
                    }
                }
            }
        }


    }

    fun deletePost(post: Post, index: Int) {
        viewModelScope.launch(IO) {
            try {
                postDao.deletePost(post)
            } catch (e: Exception) {

            }
        }
    }

    fun updatePost(copy: Post) {
        viewModelScope.launch(IO) {
            try {
                postDao.updatePost(copy)
            } catch (e: Exception) {
            }
        }
    }


    data class PostState(
        val loading: Boolean = false,
        val error: String = "",
        val posts: List<Post> = emptyList(),
    )



    sealed class DataState<T>(val data: T? = null, val message: String? = null) {
        class Success<T>(data: T) : DataState<T>(data)
        class Error<T>(message: String? = "", data: T? = null) : DataState<T>(data, message)
        class Loading<T>(data: T? = null) : DataState<T>(data)
    }


}
