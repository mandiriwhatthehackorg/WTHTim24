package com.tim24.investmentteam24.repo

import android.content.Context
import com.tim24.investmentteam24.data.Goal
import com.tim24.investmentteam24.database
import org.jetbrains.anko.db.*

class GoalRepoImpl(private val context: Context) : GoalRepo {
    private val tableName = Goal.TABLE_NAME
    private val columnId = Goal.ID
    private val columnTitle = Goal.TITLE
    private val columnCurrent = Goal.CURRENT_BALANCE
    private val columnTarget = Goal.TARGET_BALANCE
    private val columnPeriod = Goal.PERIOD

    override fun getGoalList(): List<Goal> {
        val favList = mutableListOf<Goal>()
        context.database.use {
            val query =
                select("'$tableName'")
                    .orderBy(columnId, SqlOrderDirection.ASC)

            query.exec {
                if (this.count > 0)
                    this.moveToFirst()
                do {
                    val title = this.getString(this.getColumnIndexOrThrow(columnTitle))
                    val currentBalance = this.getString(this.getColumnIndexOrThrow(columnCurrent))
                    val targetBalance = this.getString(this.getColumnIndexOrThrow(columnTarget))
                    val period = this.getString(this.getColumnIndexOrThrow(columnPeriod))

                    favList.add(Goal(
                        title,
                        currentBalance,
                        targetBalance,
                        period
                    ))
                } while (this.moveToNext())
            }
        }
        return favList
    }

    override fun addGoal(goalData: Goal) {
        context.database.use {
            insert("'$tableName'",
                columnTitle to goalData.title,
                columnCurrent to goalData.currentBalance,
                columnTarget to goalData.targetBalance,
                columnPeriod to goalData.period)
        }
    }

    override fun updateNewBalance(goalTitle: String, amount: String) {
        context.database.use {
            update("'$tableName'",
                columnCurrent to amount)
                .whereArgs(
                    "$columnTitle = {title}",
                    "title" to goalTitle)
                .exec()
        }
    }

    override fun removeGoal(goalTitle: String) {
        context.database.use {
            delete("'$tableName'",
                "$columnTitle = {goalTitle}",
                "goalTitle" to goalTitle)
        }
    }

    override fun getGoalCount(): Int {
        var goalCount = 0
        context.database.use {
            select("'$tableName'")
                .exec {
                    goalCount = this.count
                }
        }
        return goalCount
    }
}