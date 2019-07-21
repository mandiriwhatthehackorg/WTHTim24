package com.tim24.investmentteam24.data


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class TransferResponse (
    val Response: TransferDetail
)

@Parcelize
data class TransferDetail (
    val availableBalance: String
) : Parcelable

