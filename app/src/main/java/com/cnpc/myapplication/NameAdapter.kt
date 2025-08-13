package com.cnpc.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class NameAdapter(private val names: List<String>, private val personInfoList: List<PersonInfo>) :
    RecyclerView.Adapter<NameAdapter.NameViewHolder>() {

    class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return NameViewHolder(view)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val name = names[position]
        holder.nameTextView.text = name

        holder.itemView.setOnClickListener {
            val personInfo = personInfoList[position]
            val gson = Gson()
            val json = gson.toJson(listOf(personInfo))
            val intent = Intent(holder.itemView.context, OneInfoActivity::class.java)
            intent.putExtra("oneinfo", json)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }
}