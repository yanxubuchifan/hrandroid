package com.cnpc.myapplication

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import android.widget.TableLayout
import android.widget.TableRow

class OneInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_one_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println("我是个人页")

        // 获取传递过来的数据（简化解析逻辑）
        val oneinfo = intent.getStringExtra("oneinfo")
        val gson = Gson()
        // 直接解析单个对象，无需解析数组
        val personInfo = gson.fromJson(oneinfo, PersonInfo::class.java)

        // 展示姓名（其他信息展示代码保持不变）
        val nameTextView: TextView = findViewById(R.id.name_text_view)
        nameTextView.text = "姓名: ${personInfo.oneinfo_name}"

        // 展示性别
        val sexTextView: TextView = findViewById(R.id.sex_text_view)
        sexTextView.text = "性别: ${personInfo.oneinfo_sex}"

        // 展示图片
        val headpicImageView: ImageView = findViewById(R.id.headpic_image_view)
        // 修正资源名拼接
        val drawableResId =
            resources.getIdentifier(personInfo.oneinfo_headpic, "mipmap", packageName)
        println("头像资源名: ${personInfo.oneinfo_headpic}, 资源ID: $drawableResId")
        // 添加资源 ID 有效性判断
        if (drawableResId != 0) {
            val drawable: Drawable? = resources.getDrawable(drawableResId, null)
            headpicImageView.setImageDrawable(drawable)
        } else {
            // 资源未找到时显示默认头像（关键修复）
            println("未找到对应的图片资源: ${personInfo.oneinfo_headpic}")
            headpicImageView.setImageResource(R.drawable.avatar_placeholder) // 使用占位图作为默认头像
        }

        // 展示其他个人信息
        // 生日信息
        findViewById<TextView>(R.id.birthday_text_view).text = personInfo.oneinfo_birthday
        // 国籍信息
        findViewById<TextView>(R.id.nationality_text_view).text = personInfo.oneinfo_nationality
        // 籍贯信息
        findViewById<TextView>(R.id.nativeplace_text_view).text = personInfo.oneinfo_nativeplace
        // 出生地信息
        findViewById<TextView>(R.id.birthplace_text_view).text = personInfo.oneinfo_birthplace
        // 入党时间信息
        findViewById<TextView>(R.id.date_of_CPC_text_view).text = personInfo.oneinfo_date_of_CPC
        // 参加工作时间
        findViewById<TextView>(R.id.date_of_work_text_view).text = personInfo.oneinfo_date_of_work
        // 健康状况
        findViewById<TextView>(R.id.health_status_text_view).text = personInfo.oneinfo_health_status
        // 技术职称
        findViewById<TextView>(R.id.technical_position_text_view).text =
            personInfo.oneinfo_technical_position
        // 人才类别
        findViewById<TextView>(R.id.talent_text_view).text = personInfo.oneinfo_talent
        // 全日制学历
        findViewById<TextView>(R.id.full_time_schooling_text_view).text =
            personInfo.oneinfo_full_time_schooling
        // 全日制学校及专业
        findViewById<TextView>(R.id.school_and_major_text_view).text =
            personInfo.oneinfo_School_and_Major
        // 在职教育
        findViewById<TextView>(R.id.inservice_education_text_view).text =
            personInfo.oneinfo_inservice_education
        // 在职学校及专业
        findViewById<TextView>(R.id.school_and_major2_text_view).text =
            personInfo.oneinfo_School_and_Major2
        // 现任职务
        findViewById<TextView>(R.id.current_position_text_view).text =
            personInfo.oneinfo_current_position
        // 拟提职务
        findViewById<TextView>(R.id.proposed_position_text_view).text =
            personInfo.oneinfo_proposed_position
        // 拟免职务
        findViewById<TextView>(R.id.proposed_removal_text_view).text =
            personInfo.oneinfo_proposed_removal
        // 工作经历
        findViewById<TextView>(R.id.work_experience_text_view).text =
            personInfo.oneinfo_work_experience
        // 奖励情况
        findViewById<TextView>(R.id.reward_text_view).text = personInfo.oneinfo_reward
        // 年度考核情况
        findViewById<TextView>(R.id.annual_assessment_text_view).text =
            personInfo.oneinfo_annual_assessment
        // 提免理由
        findViewById<TextView>(R.id.reasons_text_view).text = personInfo.oneinfo_reasons

        // 展示家庭信息
        val familyText = personInfo.oneinfo_family.joinToString("\n") {
            "关系: ${it.oneinfo_family_mamber_relationship}, 姓名: ${it.oneinfo_family_mamber_name}, 生日: ${it.oneinfo_family_mamber_birthday}, 政治面貌: ${it.oneinfo_family_mamber_political}, 职务: ${it.oneinfo_family_mamber_position}"
        }
        // 展示家庭信息
        findViewById<TextView>(R.id.family_text_view).text = familyText

         }
    // 【修复】将点击方法移至 onCreate 外部，作为 Activity 的成员方法
    fun onHeadpicClick(view: View) {
        val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_large_image)
            val largeImageView = findViewById<ImageView>(R.id.large_image_view)
            largeImageView.setImageDrawable((view as ImageView).drawable)
            largeImageView.setOnClickListener { dismiss() }
        }
        dialog.show()
    }
        }
