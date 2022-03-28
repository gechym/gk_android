package com.example.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.room.BlogDao
import com.example.myapplication.data.room.CacheMapper
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.module.Blog
import com.example.myapplication.ui.adapter.BlogAdapter
import com.example.myapplication.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter :BlogAdapter

    @Inject
    lateinit var viewModule: MainViewModule

    @Inject
    lateinit var blogDao: BlogDao

    @Inject
    lateinit var cacheMapper: CacheMapper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        subcribeObservers()
        viewModule.setStateEvent(MainStateEnvent.GetBlogEvents)

        viewModule.getBlogsDao()
        viewModule.blogs.observe(this, Observer {
            setDataAdapter(it)
        })

        initEvent()
    }

    private fun initEvent(){
        binding.btnAddBlog.setOnClickListener {
            val intent = Intent(this,AddActivity::class.java)
            startActivity(intent)
        }

        binding.search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().isNullOrEmpty()){
                    viewModule.getBlogsDao()
                }else{
                    viewModule.getBlogsDaoByTitle(s.toString())
                }
            }
        })


    }




    private fun subcribeObservers () {
        viewModule.dataState.observe(this, Observer {dataState ->
            when(dataState){
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)

                        viewModule.getBlogsDao()

                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.toString())
                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?){
        if (message != null){
            binding.text.append(message)
        }else{
            binding.text.append("No data")
        }
    }

    private fun displayProgressBar(isDisplay : Boolean){
        binding.progressBar.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }

    private fun setDataAdapter(blog: List<Blog>) {
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager =LinearLayoutManager(this)

        adapter = BlogAdapter(blog,onDelete,onClick,onViewDetail)

        binding.recyclerview.adapter = adapter
    }



    val onClick: (blog : Blog) -> Unit = {
        Toast.makeText(this, "asdkjasdlk", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, UpdateActivity::class.java)

        intent.putExtra("blog",it)

        startActivity(intent)
    }

    val onDelete: (blog : Blog) -> Unit = {
        viewModule.deleteBlogDao(it)
    }

    val onViewDetail: (blog : Blog) -> Unit = {
        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("blog",it)

        startActivity(intent)
    }


}

