package com.cnpc.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class InfoListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        // 隐藏 ActionBar
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println("我是信息列表页")
        // 获取传递过来的数据
        val infolist = intent.getStringExtra("infolist")
        val gson = Gson()
        val receivedList = gson.fromJson(infolist, Array<PersonInfo>::class.java).toMutableList()
        println(receivedList)

        // 提取名字列表
        val names = receivedList.map { it.oneinfo_name }

        // 设置 RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NameAdapter(names, receivedList)
        recyclerView.adapter = adapter
    }
}