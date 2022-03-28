package com.example.myapplication.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.module.Blog
import com.example.myapplication.reponsitory.MainReponsitory
import com.example.myapplication.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModule
@Inject
 constructor(
    private val repository: MainReponsitory,
) : ViewModel() {
    private val _dataState : MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    private val _blogs : MutableLiveData<List<Blog>> = MutableLiveData()

    val blogs: LiveData<List<Blog>>
        get() = _blogs

    fun setStateEvent(mainStateEnvent: MainStateEnvent){
        viewModelScope.launch {
            when(mainStateEnvent){
                is MainStateEnvent.GetBlogEvents -> {
                    repository.getBlog()
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    fun getBlogsDaoByTitle(title: String){
        viewModelScope.launch {
            _blogs.value = repository.findBlogDao(title)
            Log.e("CheckLog",_blogs.value.toString())
        }
    }


    fun getBlogsDao()  {
        viewModelScope.launch {
            _blogs.value = repository.getBlogsDao()

        }
    }

    fun updateBlogDao(blog: Blog){
        viewModelScope.launch {
            repository.updataBlogDao(blog)

            getBlogsDao()
        }
    }

    fun deleteBlogDao(blog: Blog){
        viewModelScope.launch {
            repository.deleteBlogDao(blog)



            getBlogsDao()
        }
    }

    fun insertBlogDao(blog: Blog){
        viewModelScope.launch {
            repository.insertBlogDao(blog)
            getBlogsDao()
        }
    }


}

sealed class MainStateEnvent{
    object GetBlogEvents : MainStateEnvent()
    object None : MainStateEnvent()
}