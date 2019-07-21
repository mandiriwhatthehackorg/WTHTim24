package com.tim24.investmentteam24.data

data class TransferData (
    var amount: String,
    var beneficiaryAccountNumber: String,
    val sourceAccountNumber: String = "1111006399815",
    val currency: String = "IDR",
    val sourceAccountCustType: String = "1",
    val beneficiaryAccountCustType: String = "1",
    val transactionID: String = "",
    val transactionDate: String = "",
    val referenceID: String = "",
    val remark1: String = "",
    val remark2: String = ""
)

data class TransferRequest (
    val Request: TransferData
)