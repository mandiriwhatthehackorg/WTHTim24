package com.tim24.investmentteam24.features

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.tim24.investmentteam24.AppConstants.INVEST_BALANCE_KEY
import com.tim24.investmentteam24.AppConstants.PREFERENCE
import com.tim24.investmentteam24.R
import com.tim24.investmentteam24.adapter.VendorAdapter
import com.tim24.investmentteam24.addUnit
import com.tim24.investmentteam24.data.Goal
import com.tim24.investmentteam24.data.Vendor
import com.tim24.investmentteam24.getDecimalFormattedString
import com.tim24.investmentteam24.init
import kotlinx.android.synthetic.main.activity_list_vendors.*

class ListVendorsActivity : AppCompatActivity() {
    companion object {
        private const val DEPOSIT_REQUEST_CODE = 101
    }

    private lateinit var mManager: LinearLayoutManager
    private lateinit var adapterVendor : VendorAdapter
    private val listVendors = mutableListOf<Vendor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_vendors)
        val goalData = intent.getParcelableExtra<Goal>("goalData")
        mManager = LinearLayoutManager(this)
        adapterVendor = VendorAdapter(this, listVendors) { vendorData ->
            val intent = Intent(this, AddDepositActivity::class.java)
            intent.putExtra("vendorData", vendorData)
            intent.putExtra("goalData", goalData)

            startActivityForResult(intent, DEPOSIT_REQUEST_CODE)
        }
        val sharedPrefs = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)

        val currentInvestBalance = getDecimalFormattedString(
            sharedPrefs.getLong(INVEST_BALANCE_KEY, 0).toString()
        ).addUnit("Rp.", true)
        strInvestBalance.text = currentInvestBalance

        rvVendors.init(mManager)
        rvVendors.adapter = adapterVendor

        listVendors.add(Vendor(
            "Trim Kas 2",
            "5.33",
            "10000",
            "Rp 1,50 Triliun"
        ))

        listVendors.add(Vendor(
            "Mandiri Investa Pasar Uang",
            "5.02",
            "50000",
            "Rp 6,36 Triliun"
        ))

        listVendors.add(Vendor(
            "Trimegah Kas Syariah",
            "5.12",
            "100000",
            "Rp 183,20 Milyar"
        ))

        listVendors.add(Vendor(
            "Mandiri Pasar Uang Syariah",
            "5.95",
            "100000",
            "Rp 167,15 Milyar"
        ))
        adapterVendor.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DEPOSIT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val returnIntent = Intent()
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
}
