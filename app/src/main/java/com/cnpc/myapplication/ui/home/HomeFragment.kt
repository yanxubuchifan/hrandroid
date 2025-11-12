package com.cnpc.myapplication.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cnpc.myapplication.databinding.FragmentHomeBinding
import com.cnpc.myapplication.DatabaseHelper
import com.cnpc.myapplication.InfoListActivity
import com.cnpc.myapplication.PersonInfo
import com.cnpc.myapplication.SearchHistoryAdapter
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var historyAdapter: SearchHistoryAdapter
    private val historyList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        databaseHelper = DatabaseHelper(requireContext())

        // HomeFragment 中初始化 Adapter 的地方修改为：
        historyAdapter = SearchHistoryAdapter(historyList,
            onItemClick = { keyword ->
                binding.editTextText.setText(keyword)
                performSearch(keyword)
            },
            onDeleteClick = { keyword ->
                // 从列表和 SharedPreferences 中删除
                historyList.remove(keyword)
                val prefs = requireContext().getSharedPreferences("search_history", Context.MODE_PRIVATE)
                prefs.edit().putString("history", historyList.joinToString(",")).apply()
            }
        )
        binding.rvHistory.adapter = historyAdapter

        // 加载搜索历史
        loadSearchHistory()

        // 搜索框文本变化监听
        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.ivClear.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 清除文本按钮
        binding.ivClear.setOnClickListener {
            binding.editTextText.setText("")
        }

        // 搜索按钮点击
        binding.button2.setOnClickListener {
            val keyword = binding.editTextText.text.toString()
            if (keyword.isNotEmpty()) {
                saveSearchHistory(keyword)
                performSearch(keyword)
            }
        }

        // 清除历史按钮
        binding.tvClearHistory.setOnClickListener {
            clearSearchHistory()
        }

        return root
    }
    // 执行搜索
    private fun performSearch(keyword: String) {
        val allinfo = queryDatabaseByLike(keyword)
        if (allinfo.isNotEmpty()) {
            val gson = Gson()
            val json = gson.toJson(allinfo)
            val intent = Intent(requireContext(), InfoListActivity::class.java)
            intent.putExtra("infolist", json)
            startActivity(intent)
        } else {
            Toast.makeText(context, "未找到相关信息", Toast.LENGTH_SHORT).show()
        }
    }

    // 搜索历史相关方法
    private fun loadSearchHistory() {
        // 实际项目中应使用SharedPreferences或数据库存储
        val prefs = requireContext().getSharedPreferences("search_history", Context.MODE_PRIVATE)
        val historyStr = prefs.getString("history", "")
        historyList.clear()
        historyStr?.split(",")?.filter { it.isNotEmpty() }?.let {
            historyList.addAll(it)
        }
        historyAdapter.notifyDataSetChanged()
    }

    private fun saveSearchHistory(keyword: String) {
        if (historyList.contains(keyword)) {
            historyList.remove(keyword)
        }
        historyList.add(0, keyword)
        // 限制历史记录数量
        if (historyList.size > 10) {
            historyList.removeLast()
        }
        val prefs = requireContext().getSharedPreferences("search_history", Context.MODE_PRIVATE)
        prefs.edit().putString("history", historyList.joinToString(",")).apply()
        historyAdapter.notifyDataSetChanged()
    }

    private fun clearSearchHistory() {
        historyList.clear()
        requireContext().getSharedPreferences("search_history", Context.MODE_PRIVATE)
            .edit().clear().apply()
        historyAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

    // 模糊查询并遍历打印结果的函数
    private fun queryDatabaseByLike(name: String): MutableList<PersonInfo> {
        val resultList = mutableListOf<PersonInfo>()
        val cursor = databaseHelper.queryUserInfoByLike(name)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // 从游标中获取各字段的值
                val oneinfo_name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
                val oneinfo_sex = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SEX))
                val oneinfo_birthday = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BIRTHDAY))
                val oneinfo_age = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AGE))
                val oneinfo_headpic = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_HEADPIC))
                val oneinfo_nationality = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NATIONALITY))
                val oneinfo_nativeplace = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NATIVEPLACE))
                val oneinfo_birthplace = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BIRTHPLACE))
                val oneinfo_date_of_CPC = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_OF_CPC))
                val oneinfo_date_of_work = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_OF_WORK))
                val oneinfo_health_status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_HEALTH_STATUS))
                val oneinfo_technical_position = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TECHNICAL_POSITION))
                val oneinfo_talent = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TALENT))
                val oneinfo_full_time_schooling = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FULL_TIME_SCHOOLING))
                val oneinfo_School_and_Major = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCHOOL_AND_MAJOR))
                val oneinfo_inservice_education = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_INSERVICE_EDUCATION))
                val oneinfo_School_and_Major2 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCHOOL_AND_MAJOR2))
                val oneinfo_current_position = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CURRENT_POSITION))
                val oneinfo_proposed_position = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROPOSED_POSITION))
                val oneinfo_proposed_removal = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PROPOSED_REMOVAL))
                val oneinfo_work_experience = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_WORK_EXPERIENCE))
                val oneinfo_reward = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REWARD))
                val oneinfo_annual_assessment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ANNUAL_ASSESSMENT))
                val oneinfo_reasons = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REASONS))

                // 获取家庭信息的 JSON 字符串
                val familyJson = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FAMILY))
                val oneinfo_family = parseFamilyMembers(familyJson)

                // 创建 PersonInfo 对象
                val personInfo = PersonInfo(
                    oneinfo_name,
                    oneinfo_sex,
                    oneinfo_birthday,
                    oneinfo_age,
                    oneinfo_headpic,
                    oneinfo_nationality,
                    oneinfo_nativeplace,
                    oneinfo_birthplace,
                    oneinfo_date_of_CPC,
                    oneinfo_date_of_work,
                    oneinfo_health_status,
                    oneinfo_technical_position,
                    oneinfo_talent,
                    oneinfo_full_time_schooling,
                    oneinfo_School_and_Major,
                    oneinfo_inservice_education,
                    oneinfo_School_and_Major2,
                    oneinfo_current_position,
                    oneinfo_proposed_position,
                    oneinfo_proposed_removal,
                    oneinfo_work_experience,
                    oneinfo_reward,
                    oneinfo_annual_assessment,
                    oneinfo_reasons,
                    oneinfo_family
                )

                resultList.add(personInfo)
            } while (cursor.moveToNext())
            cursor.close()
        } else {
            println("未找到相关用户")
        }
        return resultList
    }

    private fun parseFamilyMembers(familyJson: String): List<PersonInfo.FamilyMember> {
        val familyList = mutableListOf<PersonInfo.FamilyMember>()
        try {
            val familyArray = JSONArray(familyJson)
            for (i in 0 until familyArray.length()) {
                val familyObject = familyArray.getJSONObject(i)
                val relationship = familyObject.getString("oneinfo_family_mamber_relationship")
                val name = familyObject.getString("oneinfo_family_mamber_name")
                val birthday = familyObject.getString("oneinfo_family_mamber_birthday")
                val political = familyObject.getString("oneinfo_family_mamber_political")
                val position = familyObject.getString("oneinfo_family_mamber_position")
                val familyMember = PersonInfo.FamilyMember(relationship, name, birthday, political, position)
                familyList.add(familyMember)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return familyList
    }

}