package com.example.myapplication.util

interface EnityMapper<Entity, DomainModule> {

    fun mapFromEntity(entity: Entity) : DomainModule

    fun mapToDonmainModule(domainModule: DomainModule) : Entity

}