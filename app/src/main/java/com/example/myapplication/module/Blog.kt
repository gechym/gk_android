package com.example.myapplication.module

import java.io.Serializable

data class Blog(
    val id: Int,
    var title: String,
    var body: String,
    var image: String,
    var category: String
): Serializable