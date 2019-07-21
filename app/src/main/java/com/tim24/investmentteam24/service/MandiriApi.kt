package com.tim24.investmentteam24.service

import com.tim24.investmentteam24.data.AccountBalanceResponse
import com.tim24.investmentteam24.data.TransferRequest
import com.tim24.investmentteam24.data.TransferResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*


interface MandiriApi{
    companion object {
        private const val VERSION = "1.0"
        private const val SERVICE_PATH = "ServicingAPI/$VERSION"
        private const val TRX_PATH = "TrxAndPaymentAPI/$VERSION"
    }

    @Headers(
        "Accept: application/json",
        "Authorization: Bearer eyJraWQiOiJqd3RrZXkiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlZTczNDdlYi00NTRlLTQ4MmQtOTlmMC01NGUxYjlhMGJlMjQiLCJhdWQiOlsiMWJmMzk5NmEtZTVkYi00ODZkLWI3NTMtOTFiY2NlZDE3ZDQzIiwibWFuZGlyaV9oYWNrYXRob25fdGVhbSJdLCJuYmYiOjE1NjM1NDY2NTEsImlzcyI6Imh0dHBzOlwvXC93d3cuYmFua21hbmRpcmkuY28uaWRcLyIsImV4cCI6MTU2Mzc2MjY1MSwiaWF0IjoxNTYzNTQ2NjUxLCJhcHBfaWQiOiIxYmYzOTk2YS1lNWRiLTQ4NmQtYjc1My05MWJjY2VkMTdkNDMifQ.VaSbk4Fts1pu6NbqhRG1xtu9MGBKxpYlad3MbvWp12t4sYRnK45RiLxOEKyvJQoHNGF422KoD-7dMToZ_QYz03U82MW78P7rWG_JoH3fNwRneW5UXS35z3lvhe2RePZ2zLmZIt0lzjQgu7W0emY1tSbPwBo3Wg6R5Tldrh0YPg8"
    )
    @GET("$SERVICE_PATH/customer/casa/{accNumber}/balance")
    fun getAccountBalance(@Path("accNumber") accNumber: String): Deferred<Response<AccountBalanceResponse>>

    @Headers(
        "Accept: application/json",
        "Authorization: Bearer eyJraWQiOiJqd3RrZXkiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlZTczNDdlYi00NTRlLTQ4MmQtOTlmMC01NGUxYjlhMGJlMjQiLCJhdWQiOlsiMWJmMzk5NmEtZTVkYi00ODZkLWI3NTMtOTFiY2NlZDE3ZDQzIiwibWFuZGlyaV9oYWNrYXRob25fdGVhbSJdLCJuYmYiOjE1NjM1NDY2NTEsImlzcyI6Imh0dHBzOlwvXC93d3cuYmFua21hbmRpcmkuY28uaWRcLyIsImV4cCI6MTU2Mzc2MjY1MSwiaWF0IjoxNTYzNTQ2NjUxLCJhcHBfaWQiOiIxYmYzOTk2YS1lNWRiLTQ4NmQtYjc1My05MWJjY2VkMTdkNDMifQ.VaSbk4Fts1pu6NbqhRG1xtu9MGBKxpYlad3MbvWp12t4sYRnK45RiLxOEKyvJQoHNGF422KoD-7dMToZ_QYz03U82MW78P7rWG_JoH3fNwRneW5UXS35z3lvhe2RePZ2zLmZIt0lzjQgu7W0emY1tSbPwBo3Wg6R5Tldrh0YPg8",
        "Content-Type: application/json"
    )
    @POST("$TRX_PATH/transfer")
    fun transferFund(@Body body: TransferRequest): Deferred<Response<TransferResponse>>
}