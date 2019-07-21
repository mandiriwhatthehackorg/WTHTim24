package com.tim24.investmentteam24.repo

import android.util.Log
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.tim24.investmentteam24.data.TransactionDetail

class TransactionRepoImpl(private val mRef: DatabaseReference) : TransactionRepo {
    companion object {
        private const val TAG = "TransactionRepoImpl"
    }
    
    private var detailTransactionSource = TaskCompletionSource<TransactionDetail?>()
    var detailTransactionTask = detailTransactionSource.task

    private var writeTransactionSource = TaskCompletionSource<Boolean>()
    var writeTransactionTask = writeTransactionSource.task

    private var updateTransactionSource = TaskCompletionSource<Boolean>()
    var updateTransactionTask = writeTransactionSource.task

    override fun reassignTask() {
        if (detailTransactionTask.isSuccessful) {
            detailTransactionSource = TaskCompletionSource()
            detailTransactionTask = detailTransactionSource.task
        }

        if (writeTransactionTask.isSuccessful) {
            writeTransactionSource = TaskCompletionSource()
            writeTransactionTask = writeTransactionSource.task
        }

        if (updateTransactionTask.isSuccessful) {
            updateTransactionSource = TaskCompletionSource()
            updateTransactionTask = updateTransactionSource.task
        }
    }

    override fun getTransactionDetail(trxId: String) {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("TransactionRepoImpl", "Data Get!")
                val transactionData = dataSnapshot.getValue(TransactionDetail::class.java)
                detailTransactionSource.trySetResult(transactionData)
                Log.d("TransactionRepoImpl", "${transactionData != null}")
                mRef.removeEventListener(this)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, databaseError.toException().localizedMessage)
                mRef.removeEventListener(this)
            }
        }

        mRef.child(trxId).addValueEventListener(listener)
    }
}