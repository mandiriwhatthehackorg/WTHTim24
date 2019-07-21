package com.tim24.investmentteam24.features.detail_transaction

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.tim24.investmentteam24.*
import com.tim24.investmentteam24.data.TransactionDetail
import com.tim24.investmentteam24.features.InputPINActivity
import com.tim24.investmentteam24.features.ShowCashbackActivity
import com.tim24.investmentteam24.repo.TransactionRepoImpl
import com.tim24.investmentteam24.service.ApiFactory
import kotlinx.android.synthetic.main.activity_detail_trx.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DetailTrxActivity : AppCompatActivity(), DetailTrxContract.View, View.OnClickListener {
    private lateinit var mPresenter: DetailTrxPresenter
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mTrxRepo: TransactionRepoImpl
    private lateinit var mTrxDetail: TransactionDetail
    private lateinit var mTrxID: String
    companion object {
        private const val PIN_REQUEST_CODE = 100
        private const val PREFERENCE = "AppPrefs"
        private const val ACC_BALANCE_KEY = "AccountBalance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_trx)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        FirebaseApp.initializeApp(this)
        mDatabase = FirebaseDatabase.getInstance()

        mTrxRepo = TransactionRepoImpl(mDatabase.getReference("transactions"))

        mTrxID = intent.getStringExtra("trxID")
        if (mTrxID.isBlank()) {
            longToast("No Transaction ID")
            finish()
        }

        val apiService = ApiFactory.mandiriApi
        mPresenter = DetailTrxPresenter(this, apiService, mTrxRepo)

        mPresenter.loadTransactionDetails(mTrxID)
        cancelButton.setOnClickListener(this)
        approveButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cancelButton -> finish()
            R.id.approveButton -> {
                startActivityForResult(Intent(this, InputPINActivity::class.java), PIN_REQUEST_CODE)
            }
            else -> {}
        }
    }

    override fun setAvailableBalance(availableBalance: String) {
        val sharedPrefs = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putLong(ACC_BALANCE_KEY, availableBalance.toLong())
        editor.apply()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PIN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                toast("PIN confirmed succesfully")
                mPresenter.transferFund(mTrxDetail.amount.toString(), mTrxDetail.recipient)
                startActivity<ShowCashbackActivity>("trxDetail" to mTrxDetail)
                finish()
            } else {
                toast("PIN confirmation failed")
            }
        }
    }

    override fun showData(trxDetail: TransactionDetail) {
        if (!this.isDestroyed) {
            mTrxDetail = trxDetail
            txtRecipientName.text = trxDetail.recipientName
            txtAmount.text = getDecimalFormattedString(trxDetail.amount.toString()).addUnit("Rp.", true)
            txtItemDetails.text = when (trxDetail.recipientName) {
                "Pizza Hut" -> "Big Box Delight x2"
                "J.CO" -> "Fruit Salad x3"
                else -> "Undefined"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::mPresenter.isInitialized) {
            mPresenter.loadData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mPresenter.isInitialized)
            mPresenter.onDestroyPresenter()
    }

    override fun hideProgressBar() {
        if (!this.isDestroyed) {
            progressCircle.hide()
            detailContainer.show()
            buttonContainer.show()
        }
    }

    override fun showProgressBar() {
        if (!this.isDestroyed) {
            progressCircle.show()
            detailContainer.hide()
            buttonContainer.hide()
        }
    }

    override fun showToast(text: String, long: Boolean?) {
        if (long == null)
            toast(text)
        else {
            if (long)
                longToast(text)
            else
                toast(text)
        }
    }

    override fun destroyActivity() {
        if (::mPresenter.isInitialized)
            mPresenter.onDestroyPresenter()
    }
}
