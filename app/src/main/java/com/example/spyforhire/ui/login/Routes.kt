package com.example.spyforhire.ui.login

import com.example.spyforhire.SendItem
import layout.Item
import retrofit2.Call
import retrofit2.http.*

interface Routes {

    @GET("item")
    fun getItems() : Call<Int>
    @Headers("Content-Type: application/json")
    @POST("item") fun sendItem(@Body userData: SendItem): Call<SendItem>


    @GET("users") fun getUser() : Call<User>
    @Headers("Content-Type: application/json")
    @POST("login") fun newUser(@Body userData: User): Call<User>
}