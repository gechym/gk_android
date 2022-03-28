package com.example.myapplication.dependency_Injection

import com.example.myapplication.data.retrofit.NetworkMapper
import com.example.myapplication.data.retrofit.RetrofitBlogApi
import com.example.myapplication.data.room.BlogDao
import com.example.myapplication.data.room.CacheMapper
import com.example.myapplication.reponsitory.MainReponsitory
import com.example.myapplication.ui.MainViewModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ReponsitiryModule {

    @Singleton
    @Provides
    fun proviceMainReponsitory(
        blogDao: BlogDao,
        retrofitBlogApi: RetrofitBlogApi,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ):MainReponsitory{
        return MainReponsitory(blogDao, retrofitBlogApi,cacheMapper, networkMapper)
    }


    @Singleton
    @Provides
    fun proviceViewModule(
        reponsitory: MainReponsitory
    ) : MainViewModule {
        return MainViewModule(reponsitory)
    }
}