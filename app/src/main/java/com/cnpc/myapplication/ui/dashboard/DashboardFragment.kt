// 在 DashboardFragment.kt 中修改代码
package com.cnpc.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cnpc.myapplication.DatabaseHelper
import com.cnpc.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var databaseHelper: DatabaseHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        databaseHelper = DatabaseHelper(requireContext())

        // 展示按性别分类的比例
        val sexCountMap = databaseHelper.countBySex()
        val totalSexCount = sexCountMap.values.sum()
        var sexRatioText = "按性别分类比例：\n"
        sexCountMap.forEach { (sex, count) ->
            val ratio = (count.toFloat() / totalSexCount) * 100
            sexRatioText += "$sex: %.2f%%\n".format(ratio)
        }
        val sexRatioTextView = TextView(requireContext())
        sexRatioTextView.text = sexRatioText
        binding.root.addView(sexRatioTextView)

        // 展示按年龄分类的比例
        val ageGroupCountMap = databaseHelper.countByAgeGroup()
        val totalAgeGroupCount = ageGroupCountMap.values.sum()
        var ageGroupRatioText = "按年龄分类比例：\n"
        ageGroupCountMap.forEach { (ageGroup, count) ->
            val ratio = (count.toFloat() / totalAgeGroupCount) * 100
            ageGroupRatioText += "$ageGroup: %.2f%%\n".format(ratio)
        }
        val ageGroupRatioTextView = TextView(requireContext())
        ageGroupRatioTextView.text = ageGroupRatioText
        binding.root.addView(ageGroupRatioTextView)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}