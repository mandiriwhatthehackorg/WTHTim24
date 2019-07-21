package com.tim24.investmentteam24.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionDetail (
    var amount: Long = 0,
    var cashback: Int = 0,
    var recipient: String = "",
    var recipientName: String = ""
) : Parcelable