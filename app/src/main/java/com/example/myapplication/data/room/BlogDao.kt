package com.example.myapplication.data.room

import androidx.room.*

@Dao
interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogCacheEntity: BlogCacheEntity) : Long

    @Delete
    suspend fun delete(blogCacheEntity: BlogCacheEntity)

    @Update
    suspend fun update(blogCacheEntity: BlogCacheEntity)

    @Query("SELECT * FROM Blogs")
    suspend fun get() : List<BlogCacheEntity>

    @Query("SELECT * FROM Blogs WHERE title LIKE '%' || :title || '%'")
    suspend fun getBlogByTitle(title : String) : List<BlogCacheEntity>


}