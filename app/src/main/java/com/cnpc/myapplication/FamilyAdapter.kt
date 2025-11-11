package com.cnpc.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FamilyAdapter(private val familyList: List<PersonInfo.FamilyMember>) :
    RecyclerView.Adapter<FamilyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val relationship: TextView = itemView.findViewById(R.id.relationship)
        val name: TextView = itemView.findViewById(R.id.name)
        val birthday: TextView = itemView.findViewById(R.id.birthday)
        val political: TextView = itemView.findViewById(R.id.political)
        val position: TextView = itemView.findViewById(R.id.position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.family_table_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val member = familyList[position]
        holder.relationship.text = member.oneinfo_family_mamber_relationship
        holder.name.text = member.oneinfo_family_mamber_name
        holder.birthday.text = member.oneinfo_family_mamber_birthday
        holder.political.text = member.oneinfo_family_mamber_political
        holder.position.text = member.oneinfo_family_mamber_position
    }

    override fun getItemCount() = familyList.size
}