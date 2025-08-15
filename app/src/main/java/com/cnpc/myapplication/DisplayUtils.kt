package com.cnpc.myapplication

import android.content.Context
import android.util.TypedValue

// dp转px的扩展函数
fun Int.dpToPx(context: Context = App.context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

// 需要在Application中初始化App.context（如果没有的话）
object App {
    lateinit var context: Context
}
