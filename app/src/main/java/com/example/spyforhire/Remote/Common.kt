package com.example.spyforhire.Remote

import com.example.spyforhire.Model.Results

object Common {
    private val GOOGLE_API_URL="https://maps.googleapis.com/"
    var commonResults:Results?=null
    val googleApiService:IGoogleAPIService
        get()=RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}