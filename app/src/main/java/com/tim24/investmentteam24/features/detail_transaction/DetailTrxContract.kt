package com.tim24.investmentteam24.features.detail_transaction

import com.tim24.investmentteam24.data.TransactionDetail

interface DetailTrxContract {
    interface View {
        fun showToast(text: String, long: Boolean? = false)
        // fun showData(accountData: AccountBalance)
        fun destroyActivity()
        fun showProgressBar()
        fun hideProgressBar()
        fun showData(trxDetail: TransactionDetail)
        fun setAvailableBalance(availableBalance: String)
    }

    interface Presenter {
        fun onDestroyPresenter()
        fun loadData()
        fun loadTransactionDetails(trxId: String)
        fun transferFund(amount: String, destAccNum: String)
    }
}