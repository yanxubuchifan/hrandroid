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

        // 展示图片
        val headpicImageView: ImageView = findViewById(R.id.headpic_image_view)
        // 修正资源名拼接
        val drawableResId = resources.getIdentifier(personInfo.oneinfo_headpic, "drawable", packageName)
        println(personInfo.oneinfo_headpic)
        // 添加资源 ID 有效性判断
        if (drawableResId != 0) {
            val drawable: Drawable? = resources.getDrawable(drawableResId, null)
            headpicImageView.setImageDrawable(drawable)
        } else {
            // 可以在这里添加日志或者错误提示，比如资源未找到的处理
            println("未找到对应的图片资源")
        }

        // 展示其他个人信息
        findViewById<TextView>(R.id.birthday_text_view).text = "生日: ${personInfo.oneinfo_birthday}"
        findViewById<TextView>(R.id.nationality_text_view).text = "国籍: ${personInfo.oneinfo_nationality}"
        findViewById<TextView>(R.id.nativeplace_text_view).text = "籍贯: ${personInfo.oneinfo_nativeplace}"
        findViewById<TextView>(R.id.birthplace_text_view).text = "出生地: ${personInfo.oneinfo_birthplace}"
        findViewById<TextView>(R.id.date_of_CPC_text_view).text = "入党时间: ${personInfo.oneinfo_date_of_CPC}"
        findViewById<TextView>(R.id.date_of_work_text_view).text = "参加工作时间: ${personInfo.oneinfo_date_of_work}"
        findViewById<TextView>(R.id.health_status_text_view).text = "健康状况: ${personInfo.oneinfo_health_status}"
        findViewById<TextView>(R.id.technical_position_text_view).text = "技术职称: ${personInfo.oneinfo_technical_position}"
        findViewById<TextView>(R.id.talent_text_view).text = "人才类别: ${personInfo.oneinfo_talent}"
        findViewById<TextView>(R.id.full_time_schooling_text_view).text = "全日制学历: ${personInfo.oneinfo_full_time_schooling}"
        findViewById<TextView>(R.id.school_and_major_text_view).text = "全日制学校及专业: ${personInfo.oneinfo_School_and_Major}"
        findViewById<TextView>(R.id.inservice_education_text_view).text = "在职教育: ${personInfo.oneinfo_inservice_education}"
        findViewById<TextView>(R.id.school_and_major2_text_view).text = "在职学校及专业: ${personInfo.oneinfo_School_and_Major2}"
        findViewById<TextView>(R.id.current_position_text_view).text = "现任职务: ${personInfo.oneinfo_current_position}"
        findViewById<TextView>(R.id.proposed_position_text_view).text = "拟提职务: ${personInfo.oneinfo_proposed_position}"
        findViewById<TextView>(R.id.proposed_removal_text_view).text = "拟免职务: ${personInfo.oneinfo_proposed_removal}"
        findViewById<TextView>(R.id.work_experience_text_view).text = "工作经历: ${personInfo.oneinfo_work_experience}"
        findViewById<TextView>(R.id.reward_text_view).text = "奖励情况: ${personInfo.oneinfo_reward}"
        findViewById<TextView>(R.id.annual_assessment_text_view).text = "年度考核情况: ${personInfo.oneinfo_annual_assessment}"
        findViewById<TextView>(R.id.reasons_text_view).text = "提免理由: ${personInfo.oneinfo_reasons}"

        // 展示家庭信息
        val familyText = personInfo.oneinfo_family.joinToString("\n") {
            "关系: ${it.oneinfo_family_mamber_relationship}, 姓名: ${it.oneinfo_family_mamber_name}, 生日: ${it.oneinfo_family_mamber_birthday}, 政治面貌: ${it.oneinfo_family_mamber_political}, 职务: ${it.oneinfo_family_mamber_position}"
        }
        findViewById<TextView>(R.id.family_text_view).text = "家庭信息:\n$familyText"
    }
}