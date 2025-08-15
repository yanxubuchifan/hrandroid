package com.cnpc.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.text.ParseException // Add this import for ParseException
import java.util.Calendar
import java.util.Locale
// 修改构造函数，仅保留personInfoList参数
class NameAdapter(private val personInfoList: List<PersonInfo>) :
    RecyclerView.Adapter<NameAdapter.NameViewHolder>() {

    // 更新ViewHolder以包含性别和生日TextView
    class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_text)
        val genderTextView: TextView = itemView.findViewById(R.id.gender_text)
        val birthdayTextView: TextView = itemView.findViewById(R.id.birthday_text) // 添加生日TextView引用
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        // 使用自定义列表项布局（替换系统默认布局）
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person_info, parent, false) // 确保使用item_person_info.xml
        return NameViewHolder(view)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val person = personInfoList[position]
        holder.nameTextView.text = person.oneinfo_name
        holder.genderTextView.text = "性别: ${person.oneinfo_sex}"
        holder.birthdayTextView.text = "生日: ${person.oneinfo_birthday}" // 绑定生日数据
        // ... 点击事件逻辑保持不变 ...

        // 保留原有点击事件逻辑
        holder.itemView.setOnClickListener {
            val gson = Gson()
            val json = gson.toJson(person)
            val intent = Intent(holder.itemView.context, OneInfoActivity::class.java)
            intent.putExtra("oneinfo", json)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = personInfoList.size
}
