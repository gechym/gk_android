package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.example.myapplication.databinding.ActivityUpdateBinding
import com.example.myapplication.module.Blog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBinding

    @Inject
    lateinit var viewModule: MainViewModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val blog = intent.getSerializableExtra("blog") as Blog
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        binding.apply {
            this.bodyText.setText(blog.body, TextView.BufferType.EDITABLE)
            this.titleText.setText(blog.title, TextView.BufferType.EDITABLE)
            this.category.setText(blog.category, TextView.BufferType.EDITABLE)
            this.imageUrl.setText(blog.image, TextView.BufferType.EDITABLE)



            binding.button.setOnClickListener {
                blog.title = binding.titleText.text.toString()
                blog.body = binding.bodyText.text.toString()
                blog.category = binding.category.text.toString()
                blog.image = binding.imageUrl.text.toString()


                viewModule.updateBlogDao(blog)
                finish()
            }

        }





    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}