package com.example.myapplication.data.retrofit

import retrofit2.http.GET

interface RetrofitBlogApi {
    @GET("blogs")
    suspend fun get() : List<BlogNetworkEntity>


}