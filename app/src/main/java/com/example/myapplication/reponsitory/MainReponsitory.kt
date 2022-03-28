package com.example.myapplication.reponsitory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.retrofit.BlogNetworkEntity
import com.example.myapplication.data.retrofit.NetworkMapper
import com.example.myapplication.data.retrofit.RetrofitBlogApi
import com.example.myapplication.data.room.BlogCacheEntity
import com.example.myapplication.data.room.BlogDao
import com.example.myapplication.data.room.CacheMapper
import com.example.myapplication.module.Blog
import com.example.myapplication.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MainReponsitory
@Inject
constructor(
    private val blogDao: BlogDao,
    private val retrofitBlogApi: RetrofitBlogApi,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper,
) {



     suspend fun getBlogsDao(): List<Blog> {
        val blogsEntity: List<BlogCacheEntity> = blogDao.get()
        return cacheMapper.mapFromEntityList(blogsEntity)
    }

    suspend fun updataBlogDao(blog : Blog) {
        blogDao.update(cacheMapper.mapToDonmainModule(blog))
    }

    suspend fun insertBlogDao(blog : Blog){
        blogDao.insert(cacheMapper.mapToDonmainModule(blog))
    }

    suspend fun deleteBlogDao(blog : Blog){
        blogDao.delete(cacheMapper.mapToDonmainModule(blog))
    }

    suspend fun findBlogDao(title : String) : List<Blog>{
         val blog: List<BlogCacheEntity> = blogDao.getBlogByTitle(title)
        return cacheMapper.mapFromEntityList(blog)
    }

    fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        try {
            val blogNetworkEntities: List<BlogNetworkEntity> = retrofitBlogApi.get()
            val blogs: List<Blog> = networkMapper.mapFromEntityList(blogNetworkEntities)

            for (blog in blogs) {

                blogDao.insert(cacheMapper.mapToDonmainModule(blog))
            }

            val blogCacheEntities : List<BlogCacheEntity> = blogDao.get()

            emit(DataState.Success(cacheMapper.mapFromEntityList(blogCacheEntities)))

        } catch (e: Exception) {
            Log.e("checkApp", e.toString())
            emit(DataState.Error(e))
        }
    }

}