package com.tim24.investmentteam24.features

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.tim24.investmentteam24.R
import com.tim24.investmentteam24.adapter.GoalAdapter
import com.tim24.investmentteam24.data.Goal
import com.tim24.investmentteam24.init
import com.tim24.investmentteam24.repo.GoalRepoImpl
import kotlinx.android.synthetic.main.activity_list_goals.*

class ListGoalsActivity : AppCompatActivity() {
    companion object {
        private const val SELECT_REQUEST_CODE = 102
    }

    private lateinit var mManager: LinearLayoutManager
    private lateinit var adapterGoal : GoalAdapter
    private val listGoals = mutableListOf<Goal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_goals)

        mManager = LinearLayoutManager(this)
        adapterGoal = GoalAdapter(this, listGoals) { goalData ->
            val intent = Intent(this, ListVendorsActivity::class.java)
            intent.putExtra("goalData", goalData)

            startActivityForResult(intent, SELECT_REQUEST_CODE)
        }

        rvGoals.init(mManager)
        rvGoals.adapter = adapterGoal

        val goalRepo = GoalRepoImpl(this)
        val goalsList = goalRepo.getGoalList()
        listGoals.clear()
        listGoals.addAll(goalsList)
        adapterGoal.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                finish()
            }
        }
    }
}
