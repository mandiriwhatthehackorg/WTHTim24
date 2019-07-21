package com.tim24.investmentteam24.features

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tim24.investmentteam24.AppConstants.INVEST_BALANCE_KEY
import com.tim24.investmentteam24.AppConstants.PREFERENCE
import com.tim24.investmentteam24.NumberTextWatcherForThousand
import com.tim24.investmentteam24.R
import com.tim24.investmentteam24.data.Goal
import com.tim24.investmentteam24.data.Vendor
import com.tim24.investmentteam24.repo.GoalRepoImpl
import com.tim24.investmentteam24.submitInput
import com.tim24.investmentteam24.trimCommaOfString
import kotlinx.android.synthetic.main.activity_add_deposit.*
import org.jetbrains.anko.toast
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

class AddDepositActivity : AppCompatActivity() {
    private lateinit var sharedPrefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_deposit)
        sharedPrefs = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        val goalData = intent.getParcelableExtra<Goal>("goalData")
        val vendorData = intent.getParcelableExtra<Vendor>("vendorData")
        val currentInvestBalance = sharedPrefs.getLong(INVEST_BALANCE_KEY, 0)

        inputDeposit.addTextChangedListener(NumberTextWatcherForThousand(inputDeposit))
        saveButton.setOnClickListener {
            val inputDeposit = trimCommaOfString(inputDeposit.submitInput())
            val profit = BigDecimal(inputDeposit)
                .multiply(vendorData.oneYearRate.toBigDecimal())
                .divide(BigDecimal(100), RoundingMode.FLOOR)
                .toBigInteger()
            val profitDeposit = BigInteger(inputDeposit)
                .add(profit)
            if (currentInvestBalance < inputDeposit.toLong()) {
                toast("Your Investment Balance is not sufficient, please enter the amount in your Investment Balance range")
            } else if (inputDeposit.toLong() < vendorData.minimalPurchase.toLong()){
                toast("Please enter deposit amount above minimum purchase")
            } else {
                val newInvestBalance = currentInvestBalance.toBigInteger().minus(inputDeposit.toBigInteger())
                val newGoalBalance = goalData.currentBalance.toBigInteger().add(profitDeposit)

                addNewBalance(goalData.title, newGoalBalance.toString())
                val editor = sharedPrefs.edit()
                editor.putLong(INVEST_BALANCE_KEY, newInvestBalance.toLong())
                editor.apply()

                val returnIntent = Intent()
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
                toast("Investment completed successfully! Your Goal Current Balance will be updated")
            }
        }
        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun addNewBalance(goalTitle: String, amount: String) {
        val goalRepo = GoalRepoImpl(this)
        goalRepo.updateNewBalance(goalTitle, amount)
    }
}
