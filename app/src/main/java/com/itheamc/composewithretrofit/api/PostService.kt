package com.itheamc.composewithretrofit.api


import com.itheamc.composewithretrofit.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("/posts")
    suspend fun getPosts(): Response<Post>
}