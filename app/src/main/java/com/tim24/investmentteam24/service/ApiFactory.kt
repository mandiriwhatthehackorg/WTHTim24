package com.tim24.investmentteam24.service

import com.tim24.investmentteam24.AppConstants

object ApiFactory {
    val mandiriApi : MandiriApi = RetrofitFactory.retrofit(AppConstants.API_BASE_URL)
        .create(MandiriApi::class.java)
}