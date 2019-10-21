package com.example.livedataexample.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.GridLayout
import com.example.livedataexample.R
import com.example.livedataexample.model.Blog
import com.example.livedataexample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var blogAdapter: BlogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        getPopularBlogs()

        swiperefresh.setOnRefreshListener { getPopularBlogs() }
    }

    private fun getPopularBlogs() {
        swiperefresh.isRefreshing = false
        mainViewModel.allBlogs.observe(this, Observer { blogList ->
            prepareRecyclerView(blogList)
        })
    }

    private fun prepareRecyclerView(blogList : List<Blog>?) {
        blogAdapter = BlogAdapter(blogList, this)

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            blogRecyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            blogRecyclerView.layoutManager = GridLayoutManager(this, 4)
        }

        blogRecyclerView.itemAnimator = DefaultItemAnimator()
        blogRecyclerView.adapter = blogAdapter
    }
}
