package com.tim24.investmentteam24.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class AccountBalanceResponse (
    val Response: AccountBalanceContainer
)

data class AccountBalanceContainer (
    val balance: AccountBalance
)

@Parcelize
data class AccountBalance (
    val accountNumber: String
) : Parcelable
