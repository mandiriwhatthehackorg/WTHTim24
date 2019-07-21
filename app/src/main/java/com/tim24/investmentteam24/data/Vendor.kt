package com.tim24.investmentteam24.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vendor (
    var title: String = "",
    var oneYearRate: String = "",
    var minimalPurchase: String = "",
    var totalManagedFund: String = ""
): Parcelable