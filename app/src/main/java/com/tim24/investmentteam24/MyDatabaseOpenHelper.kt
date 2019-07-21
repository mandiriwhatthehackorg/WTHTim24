package com.tim24.investmentteam24

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.tim24.investmentteam24.data.Goal
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "InvestTim24.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(p0: SQLiteDatabase) {
        // Here you create tables
        createTable(p0, "all")
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        dropTable(p0, "all")
        createTable(p0, "all")
    }

    fun createTable(p0: SQLiteDatabase, param: String){
        if (param == "goals" || param == "all")
            p0.createTable(Goal.TABLE_NAME, true,
                Goal.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Goal.TITLE to TEXT + NOT_NULL,
                Goal.CURRENT_BALANCE to TEXT + NOT_NULL,
                Goal.TARGET_BALANCE to TEXT + NOT_NULL,
                Goal.PERIOD to TEXT + NOT_NULL
                )
    }

    fun dropTable(p0: SQLiteDatabase, param: String){
        if (param == "goals" || param == "all")
            p0.dropTable(Goal.TABLE_NAME, true)
    }
}
// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)