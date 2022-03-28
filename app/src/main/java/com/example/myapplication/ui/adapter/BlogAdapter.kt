package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.BlogItemBinding
import com.example.myapplication.module.Blog

class BlogAdapter(
    private val blogs : List<Blog>,
    val onDelete:(Blog) -> Unit,
    val onEdit:(Blog)->Unit,
    val onViewDetail:(Blog) -> Unit
    ) : RecyclerView.Adapter<BlogAdapter.viewHolder>() {
    inner class viewHolder(val binding: BlogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val body = binding.body
        val btnDelete = binding.deleteBtn
        val btnEdit = binding.editBtn
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = BlogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(layout)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val blog = blogs[position]
        holder.title.text = blog.title
        holder.body.text = blog.category

        holder.btnDelete.setOnClickListener {
            onDelete(blog)
        }
        holder.btnEdit.setOnClickListener {
            onEdit(blog)
        }
        holder.root.setOnClickListener {
            onViewDetail(blog)
        }
    }

    override fun getItemCount(): Int {
        return blogs.size
    }
}