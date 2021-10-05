package com.itheamc.composewithretrofit

import android.app.Application
import com.itheamc.composewithretrofit.api.PostService
import com.itheamc.composewithretrofit.api.RetrofitHelper
import com.itheamc.composewithretrofit.repository.PostRepository

class PostApplication: Application() {
    lateinit var postRepository: PostRepository

    override fun onCreate() {
        super.onCreate()

        // Initializing variables
        val postService = RetrofitHelper.getInstance().create(PostService::class.java)
        postRepository = PostRepository(postService = postService)
    }
}