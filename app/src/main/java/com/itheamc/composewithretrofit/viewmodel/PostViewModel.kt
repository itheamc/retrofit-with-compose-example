package com.itheamc.composewithretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itheamc.composewithretrofit.model.Post
import com.itheamc.composewithretrofit.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository): ViewModel()  {

    // whenever viewmodel initialize fetch the posts from the server
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPosts()
        }
    }

    suspend fun fetchPosts() {
        viewModelScope.launch {
            repository.getPosts()
        }
    }

    val posts: LiveData<Post>
    get() = repository.posts

    // Loading state
    val loading: LiveData<Boolean>
    get() = repository.loading
}