package com.cnpc.myapplication

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
class DatabaseHelper(val context: Context) : SQLiteOpenHelper(context, "MyDatabase2.db", null, 2) { // 提升数据库版本号

    companion object {
        const val TABLE_NAME = "users2"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "oneinfo_name"
        const val COLUMN_SEX = "oneinfo_sex"
        const val COLUMN_BIRTHDAY = "oneinfo_birthday"
        const val COLUMN_AGE = "oneinfo_age"
        const val COLUMN_HEADPIC = "oneinfo_headpic"
        const val COLUMN_NATIONALITY = "oneinfo_nationality"
        const val COLUMN_NATIVEPLACE = "oneinfo_nativeplace"
        const val COLUMN_BIRTHPLACE = "oneinfo_birthplace"
        const val COLUMN_DATE_OF_CPC = "oneinfo_date_of_CPC"
        const val COLUMN_DATE_OF_WORK = "oneinfo_date_of_work"
        const val COLUMN_HEALTH_STATUS = "oneinfo_health_status"
        const val COLUMN_TECHNICAL_POSITION = "oneinfo_technical_position"
        const val COLUMN_TALENT = "oneinfo_talent"
        const val COLUMN_FULL_TIME_SCHOOLING = "oneinfo_full_time_schooling"
        const val COLUMN_SCHOOL_AND_MAJOR = "oneinfo_School_and_Major"
        const val COLUMN_INSERVICE_EDUCATION = "oneinfo_inservice_education"
        const val COLUMN_SCHOOL_AND_MAJOR2 = "oneinfo_School_and_Major2"
        const val COLUMN_CURRENT_POSITION = "oneinfo_current_position"
        const val COLUMN_PROPOSED_POSITION = "oneinfo_proposed_position"
        const val COLUMN_PROPOSED_REMOVAL = "oneinfo_proposed_removal"
        const val COLUMN_WORK_EXPERIENCE = "oneinfo_work_experience"
        const val COLUMN_REWARD = "oneinfo_reward"
        const val COLUMN_ANNUAL_ASSESSMENT = "oneinfo_annual_assessment"
        const val COLUMN_REASONS = "oneinfo_reasons"
        const val COLUMN_FAMILY = "oneinfo_family"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 添加日志语句
        println("***********************************")
        println("***********************************")
        println("***********************************")
        println("***********************************")
        println("新建数据库")
        android.util.Log.d("DatabaseHelper", "onCreate method called, creating table $TABLE_NAME")
        db.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, $COLUMN_SEX TEXT, $COLUMN_BIRTHDAY TEXT, $COLUMN_AGE TEXT, $COLUMN_HEADPIC TEXT, " +
                "$COLUMN_NATIONALITY TEXT, $COLUMN_NATIVEPLACE TEXT, $COLUMN_BIRTHPLACE TEXT, " +
                "$COLUMN_DATE_OF_CPC TEXT, $COLUMN_DATE_OF_WORK TEXT, $COLUMN_HEALTH_STATUS TEXT, " +
                "$COLUMN_TECHNICAL_POSITION TEXT, $COLUMN_TALENT TEXT, $COLUMN_FULL_TIME_SCHOOLING TEXT, " +
                "$COLUMN_SCHOOL_AND_MAJOR TEXT, $COLUMN_INSERVICE_EDUCATION TEXT, $COLUMN_SCHOOL_AND_MAJOR2 TEXT, " +
                "$COLUMN_CURRENT_POSITION TEXT, $COLUMN_PROPOSED_POSITION TEXT, $COLUMN_PROPOSED_REMOVAL TEXT, " +
                "$COLUMN_WORK_EXPERIENCE TEXT, $COLUMN_REWARD TEXT, $COLUMN_ANNUAL_ASSESSMENT TEXT, " +
                "$COLUMN_REASONS TEXT, $COLUMN_FAMILY TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 添加日志语句
        println("***********************************")
        println("***********************************")
        println("***********************************")
        println("***********************************")
        println("升级数据库")
        android.util.Log.d("DatabaseHelper", "onUpgrade method called, dropping and recreating table $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // 模糊查询用户信息的方法
    fun queryUserInfoByLike(name: String): Cursor? {
        println("***********************************")
        println("***********************************")
        println("***********************************")
        println("***********************************")
        println("模糊查询用户信息的方法")
        val db = readableDatabase
        val selection = "$COLUMN_NAME LIKE?"
        val selectionArgs = arrayOf("%$name%")
        return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
    }

    // 按性别统计人数
    fun countBySex(): Map<String, Int> {
        println("***********************************")
        println("***********************************")
        println("***********************************")
        println("***********************************")
        println("按性别统计人数")
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_SEX, COUNT(*) FROM $TABLE_NAME GROUP BY $COLUMN_SEX", null)
        val result = mutableMapOf<String, Int>()
        if (cursor.moveToFirst()) {
            do {
                val sex = cursor.getString(0)
                val count = cursor.getInt(1)
                result[sex] = count
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }



    // 简单模拟计算年龄的方法，实际需要根据日期格式正确计算
    private fun calculateAge(birthday: String): Int {
        // 这里简单返回一个固定值，实际需要实现日期计算逻辑
        return 30
    }

    init {
        copyDatabaseIfNotExists()
    }

    private fun copyDatabaseIfNotExists() {
        // 现在可以正常访问 context（已声明为成员属性）
//        val dbPath = context.getDatabasePath("MyDatabase.db").path
//        if (!File(dbPath).exists()) {
//            context.assets.open("MyDatabase.db").use { input ->
//                FileOutputStream(dbPath).use { output ->
//                    input.copyTo(output)
//                }
//            }
//        }
        val dbFile = context.getDatabasePath("MyDatabase2.db")
        if (!dbFile.parentFile.exists()) {
            dbFile.parentFile.mkdirs() // 确保父目录存在
        }
    }
    // 按5岁一个阶段统计年龄分布（如：0-4岁、5-9岁...70岁以上）
    fun countByAgeGroup(): Map<String, Int> {
        // 定义5岁间隔的年龄分组（覆盖0-70+岁）
        val ageGroups = listOf(
            "0-4岁", "5-9岁", "10-14岁", "15-19岁",
            "20-24岁", "25-29岁", "30-34岁", "35-39岁",
            "40-44岁", "45-49岁", "50-54岁", "55-59岁",
            "60-64岁", "65-69岁", "70岁以上"
        )
        val ageGroupMap = ageGroups.associateWith { 0 }.toMutableMap()

        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_BIRTHDAY), null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val birthday = cursor.getString(0)
                val age = calculateAge(birthday)

                // 根据年龄确定5岁分组
                val group = when {
                    age >= 70 -> "70岁以上"
                    age < 0 -> "0-4岁"
                    else -> {
                        val lower = (age / 5) * 5
                        val upper = lower + 4
                        "${lower}-${upper}岁"
                    }
                }

                // 【修复】累加对应分组的人数（之前缺失的核心统计逻辑）
                ageGroupMap[group] = ageGroupMap[group]?.plus(1) ?: 1

            } while (cursor.moveToNext())
        }
        cursor.close()

        return ageGroupMap.filterValues { it > 0 }
    }
}