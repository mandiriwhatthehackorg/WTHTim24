package com.tim24.investmentteam24

import android.support.design.widget.TextInputEditText
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import java.util.*

fun View.hide (){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun TextInputEditText.submitInput() : String = this.text.toString().trim()

fun getDecimalFormattedString(value: String): String {
    val lst = StringTokenizer(value, ".")
    var str1 = value
    var str2 = ""
    if (lst.countTokens() > 1) {
        str1 = lst.nextToken()
        str2 = lst.nextToken()
    }
    var str3 = ""
    var i = 0
    var j = -1 + str1.length
    if (str1[-1 + str1.length] == ',') {
        j--
        str3 = ","
    }
    var k = j
    while (true) {
        if (k < 0) {
            if (str2.isNotEmpty())
                str3 = "$str3,$str2"
            return str3
        }
        if (i == 3) {
            str3 = ".$str3"
            i = 0
        }
        str3 = str1[k] + str3
        i++
        k--
    }

}

fun trimCommaOfString(string: String): String {
    // String returnString;
    return if (string.contains(".")) {
        string.replace(".", "")
    } else {
        string
    }
}

fun String.addUnit(unit: String, isFront: Boolean = false): String {
    return if (this == "-") this
    else {
        if (isFront) StringBuilder(unit).append(" $this").toString()
        else StringBuilder(this).append(" $unit").toString()
    }
}

fun RecyclerView.init(mManager: LinearLayoutManager){
    removeAllViews()
    layoutManager = mManager
}

fun String.toRupiah(): String = getDecimalFormattedString(this).addUnit("Rp.", true)