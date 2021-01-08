package com.example.spyforhire.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit:Retrofit?=null
    fun getClient(baseUrl1:String):Retrofit{
        if(retrofit==null)
        {
            retrofit=Retrofit.Builder().baseUrl(baseUrl1).
            addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}