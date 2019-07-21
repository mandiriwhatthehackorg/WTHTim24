package com.tim24.investmentteam24.features.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.tim24.investmentteam24.R
import com.tim24.investmentteam24.adapter.GoalAdapter
import com.tim24.investmentteam24.addUnit
import com.tim24.investmentteam24.data.Goal
import com.tim24.investmentteam24.features.AddGoalActivity
import com.tim24.investmentteam24.features.ListVendorsActivity
import com.tim24.investmentteam24.features.scan.ScanActivity
import com.tim24.investmentteam24.getDecimalFormattedString
import com.tim24.investmentteam24.init
import com.tim24.investmentteam24.repo.GoalRepoImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_balance.*
import org.jetbrains.anko.startActivity
import java.math.BigInteger

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PREFERENCE = "AppPrefs"
        private const val INVEST_BALANCE_KEY = "InvestBalance"
        private const val PROJECT_BALANCE_KEY = "TotalInvest"
        private const val ACC_BALANCE_KEY = "AccountBalance"
    }

    private var menuItem: Menu? = null

    private lateinit var mManager: LinearLayoutManager
    private lateinit var adapterGoal : GoalAdapter
    private lateinit var sharedPrefs : SharedPreferences
    private val listGoals = mutableListOf<Goal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefs = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        mManager = LinearLayoutManager(this)
        adapterGoal = GoalAdapter(this, listGoals) {
                goalData -> startActivity<ListVendorsActivity>("goalData" to goalData)
        }

        rvGoals.init(mManager)
        rvGoals.adapter = adapterGoal

        initGoals()
        btnAddGoal.setOnClickListener {
            startActivity<AddGoalActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        initGoals()
        setValue()
    }

    private fun setValue() {
        setTotalProjection()

        val currentInvestBalance = getDecimalFormattedString(
            sharedPrefs.getLong(INVEST_BALANCE_KEY, 0).toString()
        ).addUnit("Rp.", true)

        val currentAccBalance = getDecimalFormattedString(
            sharedPrefs.getLong(ACC_BALANCE_KEY, 0).toString()
        ).addUnit("Rp.", true)

        val totalInvestProject = getDecimalFormattedString(
            sharedPrefs.getLong(PROJECT_BALANCE_KEY, 0).toString()
        ).addUnit("Rp.", true)

        strInvestBalance.text = currentInvestBalance
        strAccBalance.text = currentAccBalance
        strTotalInvestProject.text = totalInvestProject
    }

    private fun setTotalProjection() {
        var totalProject: BigInteger = BigInteger.valueOf(0)
        if (listGoals.size > 0){
            listGoals.forEach {
                totalProject = totalProject.add(it.currentBalance.toBigInteger())
            }
        }

        val editor = sharedPrefs.edit()
        editor.putLong(PROJECT_BALANCE_KEY, totalProject.toLong())
        editor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.payButton -> {
                startActivity<ScanActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initGoals() {
        val goalRepo = GoalRepoImpl(this)
        addGoals()
        val goalsList = goalRepo.getGoalList()
        listGoals.clear()
        listGoals.addAll(goalsList)
        adapterGoal.notifyDataSetChanged()
    }

    private fun addGoals(){
        val goalRepo = GoalRepoImpl(this)
        if (goalRepo.getGoalCount() == 0) {
            goalRepo.addGoal(
                Goal(
                    "Wedding",
                    "100000",
                    "93576912",
                    "4"
                )
            )
            goalRepo.addGoal(
                Goal(
                    "Study at Harvard",
                    "1200000",
                    "324678912",
                    "6"
                )
            )
            goalRepo.addGoal(
                Goal(
                    "Have an Apartment Block",
                    "3500000",
                    "484558221",
                    "8"
                )
            )
            goalRepo.addGoal(
                Goal(
                    "Have a Land Rover",
                    "4500000",
                    "600678912",
                    "12"
                )
            )
        }
    }
}
