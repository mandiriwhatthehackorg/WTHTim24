package com.tim24.investmentteam24.features.detail_transaction

import android.util.Log
import com.tim24.investmentteam24.data.TransferData
import com.tim24.investmentteam24.data.TransferRequest
import com.tim24.investmentteam24.getDecimalFormattedString
import com.tim24.investmentteam24.repo.TransactionRepoImpl
import com.tim24.investmentteam24.service.MandiriApi
import kotlinx.coroutines.*

class DetailTrxPresenter (private val mView: DetailTrxContract.View,
                          private val apiService: MandiriApi,
                          private val mTrxRepo: TransactionRepoImpl) : DetailTrxContract.Presenter {

    private val job: Job = SupervisorJob()
    private val mainScope = CoroutineScope(Dispatchers.Main + job)
    
    override fun loadData(){
        mView.showProgressBar()
        mainScope.launch {
            val apiRequest = apiService.getAccountBalance("1111006399815")
            val response = apiRequest.await()
            if (response.isSuccessful) {
                Log.d("DetailTrxPresenter", "Data Get!")
                val apiResponse = response.body()
                val accountData = apiResponse?.Response?.balance
                accountData?.let {
                    Log.d("DetailTrxPresenter", it.accountNumber)
                }
                Log.d("DetailTrxPresenter", (accountData != null).toString())
                withContext(Dispatchers.Main) {
                    mView.hideProgressBar()
                }
            } else {
                Log.d("DetailTrxPresenter", response.errorBody().toString())
            }
        }
    }

    override fun loadTransactionDetails(trxId: String) {
        if (trxId.isNotBlank()) {
            mainScope.launch {
                withContext(Dispatchers.Default) {
                    if (mTrxRepo.detailTransactionTask.isSuccessful)
                        mTrxRepo.reassignTask()
                    mTrxRepo.getTransactionDetail(trxId)
                }
                mTrxRepo.detailTransactionTask.addOnSuccessListener { result ->
                    mView.hideProgressBar()
                    if (mTrxRepo.detailTransactionTask.isSuccessful) {
                        if (result != null)
                            mView.showData(result)
                        else {
                            mView.showToast("Maaf, ID transaksi ini tidak valid", true)
                            mView.destroyActivity()
                        }
                    } else {
                        mView.showToast("Terjadi kesalahan, silahkan cek jaringan anda", true)
                        mView.destroyActivity()
                    }
                }.addOnFailureListener {
                    mView.showToast("Terjadi kesalahan, silahkan cek jaringan anda", true)
                    mView.destroyActivity()
                }
            }
        } else {
            mView.showToast("ID Transaksi kosong.")
        }
    }

    override fun transferFund(amount: String, destAccNum: String){
        mView.showProgressBar()
        mainScope.launch {
            val apiRequest = apiService.transferFund(TransferRequest(TransferData(amount, destAccNum)))
            val response = apiRequest.await()
            if (response.isSuccessful) {
                Log.d("DetailTrxPresenter", "Data Get!")
                val apiResponse = response.body()
                // Log.d("DetailTrxPresenter", "$apiResponse")
                val availableBalance = apiResponse?.Response?.availableBalance
                availableBalance?.let {
                    Log.d("DetailTrxPresenter", it)
                    withContext(Dispatchers.Main) {
                        mView.setAvailableBalance(it.replace(".00",""))
                        mView.showToast("Payment completed successfully! Remaining balance: ${getDecimalFormattedString(it)}")
                    }
                }
                Log.d("DetailTrxPresenter", (availableBalance != null).toString())
            } else {
                Log.d("DetailTrxPresenter", response.errorBody().toString())
            }
            withContext(Dispatchers.Main) {
                mView.hideProgressBar()
            }
        }
    }

    override fun onDestroyPresenter() {
        if (job.isActive && !job.isCompleted)
            job.cancelChildren()
    }
}