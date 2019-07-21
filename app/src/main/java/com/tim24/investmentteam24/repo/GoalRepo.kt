package com.tim24.investmentteam24.repo

import com.tim24.investmentteam24.data.Goal

interface GoalRepo {
    fun addGoal(goalData: Goal)
    fun removeGoal(goalTitle: String)
    fun getGoalCount(): Int
    fun getGoalList(): List<Goal>
    fun updateNewBalance(goalTitle: String, amount: String)
}