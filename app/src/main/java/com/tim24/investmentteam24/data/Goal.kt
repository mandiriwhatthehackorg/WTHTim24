package com.tim24.investmentteam24.data

import android.os.Parcelable
import com.tim24.investmentteam24.addUnit
import com.tim24.investmentteam24.getDecimalFormattedString
import kotlinx.android.parcel.Parcelize
import java.math.BigInteger

@Parcelize
data class Goal (
    var title: String = "",
    var currentBalance: String = "",
    var targetBalance: String = "",
    var period: String = ""
): Parcelable {
    companion object {
        const val TABLE_NAME: String = "Goals"
        const val ID: String = "ID_"
        const val TITLE: String = "title"
        const val CURRENT_BALANCE: String = "currentBalance"
        const val TARGET_BALANCE: String = "targetBalance"
        const val PERIOD: String = "period"
    }

    fun getRecommendation(): String {
        return getDecimalFormattedString(
            targetBalance.toBigInteger()
                .divide(period.toBigInteger().multiply(BigInteger.valueOf(12)))
                .toString()
        ).addUnit("Rp.", true)
    }
}