package com.cnpc.myapplication

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class OneInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_one_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println("我是个人页")
        // 获取传递过来的数据
        val oneinfo = intent.getStringExtra("oneinfo")
        val gson = Gson()
        val receivedList = gson.fromJson(oneinfo, Array<PersonInfo>::class.java).toMutableList()
        println(receivedList)
        println(receivedList[0])
        println(receivedList[0].oneinfo_name)

        val personInfo = receivedList[0]


        // 展示姓名
        val nameTextView: TextView = findViewById(R.id.name_text_view)
        nameTextView.text = "姓名: ${personInfo.oneinfo_name}"

        // 展示性别
        val sexTextView: TextView = findViewById(R.id.sex_text_view)
        sexTextView.text = "性别: ${personInfo.oneinfo_sex}"

        // 展示照片
//        val headpicImageView: ImageView = findViewById(R.id.headpic_image_view)
//        println(personInfo.oneinfo_headpic)
//        val drawableResId = resources.getIdentifier("金闯.png", "drawable", packageName)
//        println(drawableResId)
//        val drawable: Drawable? = resources.getDrawable(drawableResId, null)
//        headpicImageView.setImageDrawable(drawable)

        // 展示图片
//        val headpicImageView: ImageView = findViewById(R.id.headpic_image_view)
//        // 修正资源名拼接
//        val drawableResId = resources.getIdentifier(
//            "headpic/金闯",
//            "drawable",
//            packageName
//        )
//        // 添加资源 ID 有效性判断
//        if (drawableResId != 0) {
//            val drawable: Drawable? = resources.getDrawable(drawableResId, null)
//            headpicImageView.setImageDrawable(drawable)
//        } else {
//            // 可以在这里添加日志或者错误提示，比如资源未找到的处理
//            println("未找到对应的图片资源")
//        }

    }
}