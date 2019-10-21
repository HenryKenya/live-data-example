package com.example.livedataexample.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.livedataexample.model.Blog
import com.example.livedataexample.networking.RestApiService
import kotlinx.coroutines.*
import java.io.IOException

class BlogRepository() {

    private var articles = mutableListOf<Blog>()
    private var mutableLiveData = MutableLiveData<List<Blog>>()

    val completableJob = Job() // what does Jobs do in coroutines
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private val restApiService by lazy {
        RestApiService.createCorService()
    }

    fun getMutableLiveData() : MutableLiveData<List<Blog>> {
        coroutineScope.launch {
            val request = restApiService.getPopularBlog()
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    val blogWrapper = response

                    if (blogWrapper.blog != null) {
                        articles = blogWrapper.blog as MutableList<Blog>
                        mutableLiveData.value = articles
                    } else {} // find out why this is necessary

                } catch (e: IOException) {
                    Log.e("BlogRepository", "An error occured: ${e.message}")
                }
            }
        }
        return mutableLiveData
    }

}