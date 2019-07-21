package com.tim24.investmentteam24.features

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.tim24.investmentteam24.R
import com.tim24.investmentteam24.addUnit
import com.tim24.investmentteam24.data.TransactionDetail
import com.tim24.investmentteam24.getDecimalFormattedString
import kotlinx.android.synthetic.main.activity_show_cashback.*
import org.jetbrains.anko.startActivity
import java.math.BigInteger

class ShowCashbackActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val PREFERENCE = "AppPrefs"
        private const val INVEST_BALANCE_KEY = "InvestBalance"
    }

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_cashback)

        val trxDetail : TransactionDetail = intent.getParcelableExtra("trxDetail")

        sharedPrefs = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        val currentBalance = sharedPrefs.getLong(INVEST_BALANCE_KEY, 0)
        val newBalance = BigInteger.valueOf(currentBalance)
            .add(
                BigInteger.valueOf(getCashback(trxDetail.cashback, trxDetail.amount))
            ).toLong()

        Log.d("CashbackActivity", "Current Investment Balance: $currentBalance")
        Log.d("CashbackActivity", "New Investment Balance: $newBalance")

        val editor = sharedPrefs.edit()
        editor.putLong(INVEST_BALANCE_KEY, newBalance)
        editor.apply()

        val strDetail = countCashback(trxDetail.cashback, trxDetail.amount)
        txtAmount.text = strDetail
        investButton.setOnClickListener(this)
        postponeButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.investButton -> {
                startActivity<ListGoalsActivity>()
                finish()
            }
            R.id.postponeButton -> {
                finish()
            }
            else -> { }
        }
    }

    private fun countCashback(cashback: Int, amount: Long): String {
        val intCashback = cashback.toBigInteger()
        val intAmount = amount.toBigInteger()
        return getDecimalFormattedString(intCashback.multiply(intAmount.divide(BigInteger.valueOf(100))).toString()).addUnit("Rp.", true)
    }

    private fun getCashback(cashback: Int, amount: Long): Long {
        val intCashback = cashback.toBigInteger()
        val intAmount = amount.toBigInteger()
        return intCashback.multiply(intAmount.divide(BigInteger.valueOf(100))).toLong()
    }
}
