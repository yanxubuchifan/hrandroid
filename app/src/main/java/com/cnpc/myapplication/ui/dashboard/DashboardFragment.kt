package com.cnpc.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cnpc.myapplication.DatabaseHelper
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.cnpc.myapplication.R
import com.cnpc.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var databaseHelper: DatabaseHelper
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        databaseHelper = DatabaseHelper(requireContext())

        // 从数据库获取并展示性别分布
        initGenderChart()
        // 从数据库获取并展示年龄分布
//        initAgeChart()

        return root
    }

    // 初始化性别分布饼图
    private fun initGenderChart() {
        val sexCountMap = databaseHelper.countBySex() // 从users表统计性别数据
        val total = sexCountMap.values.sum()

        if (total == 0) {
            binding.genderChart.description.text = "暂无性别数据"
            return
        }

        val entries = sexCountMap.map { (sex, count) ->
            PieEntry((count.toFloat() / total) * 100, "$sex (${count}人)")
        }.toMutableList()

        val dataSet = PieDataSet(entries, "性别分布")
        dataSet.colors = listOf(0xFF64B5F6.toInt(), 0xFFE91E63.toInt())

        // 创建并配置数据（设置字体大小）
        val data = PieData(dataSet)
        data.setValueTextSize(12f)  // 已设置字体大小的data变量

        binding.genderChart.apply {
            // 直接使用上面创建的data变量，无需重新创建PieData
            this.data = data  // 修复：使用已配置好的data实例
            description.text = "性别比例统计"
            isDrawHoleEnabled = true
            holeRadius = 40f
            setCenterText("性别占比")
            invalidate()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
