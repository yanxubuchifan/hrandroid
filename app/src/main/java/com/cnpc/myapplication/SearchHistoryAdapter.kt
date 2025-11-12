package com.cnpc.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchHistoryAdapter(
    private val data: MutableList<String>, // 改为 MutableList，方便删除
    private val onItemClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit // 新增删除回调
) : RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvKeyword: TextView = itemView.findViewById(R.id.tv_keyword) // 与布局中 tv_keyword 对应
        val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete) // 与布局中 iv_delete 对应
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_history, parent, false) // 加载 item 布局
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keyword = data[position]
        holder.tvKeyword.text = keyword

        // 关键词点击事件
        holder.itemView.setOnClickListener { onItemClick(keyword) }

        // 删除按钮点击事件
        holder.ivDelete.setOnClickListener {
            onDeleteClick(keyword)
            notifyItemRemoved(position) // 局部刷新，优化性能
        }
    }

    override fun getItemCount() = data.size
}