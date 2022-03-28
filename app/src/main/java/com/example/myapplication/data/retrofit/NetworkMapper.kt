package com.example.myapplication.data.retrofit

import com.example.myapplication.module.Blog
import com.example.myapplication.util.EnityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EnityMapper<BlogNetworkEntity, Blog> {
    override fun mapFromEntity(entity: BlogNetworkEntity): Blog {
        return Blog(
            entity.id,
            entity.title,
            entity.body,
            entity.image,
            entity.category,
        )
    }

    override fun mapToDonmainModule(domainModule: Blog): BlogNetworkEntity {
        return return BlogNetworkEntity(
            domainModule.id,
            domainModule.title,
            domainModule.body,
            domainModule.image,
            domainModule.category,
        )
    }

    fun mapFromEntityList(entities: List<BlogNetworkEntity>): List<Blog> {
        return entities.map {
            mapFromEntity(it)
        }
    }
}