package com.cnpc.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FamilyAdapter(private val familyList: List<PersonInfo.FamilyMember>) :
    RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>() {

    inner class FamilyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val relationText: TextView = itemView.findViewById(R.id.relation_text)
        val nameText: TextView = itemView.findViewById(R.id.name_text)
        val birthdayText: TextView = itemView.findViewById(R.id.birthday_text)
        val politicalText: TextView = itemView.findViewById(R.id.political_text)
        val positionText: TextView = itemView.findViewById(R.id.position_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_family_member, parent, false)
        return FamilyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        val member = familyList[position]
        holder.relationText.text = member.oneinfo_family_mamber_relationship
        holder.nameText.text = member.oneinfo_family_mamber_name
        holder.birthdayText.text = member.oneinfo_family_mamber_birthday
        holder.politicalText.text = member.oneinfo_family_mamber_political
        holder.positionText.text = member.oneinfo_family_mamber_position
    }

    override fun getItemCount() = familyList.size
}