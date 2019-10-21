package com.example.livedataexample.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.livedataexample.R
import com.example.livedataexample.model.Blog
import java.io.IOException

class BlogAdapter(val blogList: List<Blog>?, val context: Context) : RecyclerView.Adapter<BlogAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val view = layoutInflater.inflate(R.layout.blog_item, p0, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = if (blogList != null) blogList.size else 0

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        val blog = blogList?.get(p1)

        if (blog?.thumbnail != null) {
            Glide.with(context).load(blog.thumbnail).into(p0.thumbnail)
        }

        p0.title.text = blog?.title
        p0.description.text = blog?.description
        p0.link.text = blog?.link

        p0.link.setOnClickListener {
            if (blog?.link != null) {
                try {

                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse(blog.link)
                    context.startActivity(intent)


                } catch (e: IOException) {
                    // do something
                }
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val thumbnail: ImageView = view.findViewById(R.id.ivThumbnail)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val description: TextView = view.findViewById(R.id.tvDescription)
        val link: TextView = view.findViewById(R.id.tvLink)
    }

}