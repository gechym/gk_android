package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAddBinding
import com.example.myapplication.module.Blog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    @Inject
    lateinit var viewModule: MainViewModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.button.setOnClickListener {
            viewModule.insertBlogDao(
                Blog(
                    System.currentTimeMillis().toInt(),
                    binding.titleText.text.toString(),
                    binding.bodyText.text.toString(),
                    binding.imageUrl.text.toString(),
                    binding.category.text.toString(),
                )
            )

            finish()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }


}