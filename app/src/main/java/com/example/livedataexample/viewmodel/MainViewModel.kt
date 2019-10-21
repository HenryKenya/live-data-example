package com.example.livedataexample.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.livedataexample.model.Blog

class MainViewModel(): ViewModel() {
    val blogRepository = BlogRepository()
    val allBlogs : LiveData<List<Blog>>
        get() = blogRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        blogRepository.completableJob.cancel()
    }
}