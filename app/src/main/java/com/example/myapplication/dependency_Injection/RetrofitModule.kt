package com.example.myapplication.dependency_Injection

import com.example.myapplication.data.retrofit.RetrofitBlogApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Singleton
    @Provides
    fun proviceGsonBuilder() : Gson { //TODO Khởi tạo cung cấp đối tượng Gson cho retrofit
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun proviceRetrofit(gson: Gson) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://open-api.xyz/placeholder/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun proviceBlogService(retrofit: Retrofit.Builder) :RetrofitBlogApi {
        return retrofit.build().create(RetrofitBlogApi::class.java)
    }



}