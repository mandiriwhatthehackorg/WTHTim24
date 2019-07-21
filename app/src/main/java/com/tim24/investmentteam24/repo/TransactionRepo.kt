package com.tim24.investmentteam24.repo


interface TransactionRepo {
    fun reassignTask()
    fun getTransactionDetail(trxId: String)
}