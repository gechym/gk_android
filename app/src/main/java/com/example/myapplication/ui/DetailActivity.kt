package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.module.Blog


class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val blog = intent.getSerializableExtra("blog") as Blog

        binding.textTitle.text = blog.title
        binding.category.text = blog.category
        binding.body.text = blog.body

        Glide.with(applicationContext)
            .load(blog.image)
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_terrain_24)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .into(binding.image);


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}