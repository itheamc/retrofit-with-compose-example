package com.itheamc.composewithretrofit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itheamc.composewithretrofit.api.PostService
import com.itheamc.composewithretrofit.model.Post

class PostRepository(
    private val postService: PostService
) {

    private val postLiveData = MutableLiveData<Post>()
    val posts: LiveData<Post>
    get() = postLiveData

    // For loading management
    private val loadingState = MutableLiveData(true)
    val loading: LiveData<Boolean>
    get() = loadingState



    // Function to fetch posts from the network
    suspend fun getPosts() {
        // Start Loading
        loadingState.postValue(true)
        val response = postService.getPosts()
        if (response.body() != null) {
            postLiveData.postValue(response.body())
            loadingState.postValue(false)
        } else {
            loadingState.postValue(false)
        }
    }
}