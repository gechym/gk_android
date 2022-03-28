package com.example.myapplication.data.room

import com.example.myapplication.module.Blog
import com.example.myapplication.util.EnityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor() : EnityMapper<BlogCacheEntity, Blog>
{
    override fun mapFromEntity(entity: BlogCacheEntity): Blog {
        return Blog(
            entity.id,
            entity.title,
            entity.body,
            entity.image,
            entity.category
        )
    }

    override fun mapToDonmainModule(domainModule: Blog): BlogCacheEntity {
        return BlogCacheEntity(
            domainModule.id,
            domainModule.title,
            domainModule.body,
            domainModule.image,
            domainModule.category
        )
    }

    fun mapFromEntityList(entities : List<BlogCacheEntity>) : List<Blog>{
        return entities.map {
            mapFromEntity(it)
        }
    }
}