package com.example.spyforhire.ui.login

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("player_id") var id:Int, @SerializedName("player_name") val username:String, @SerializedName("player_password") val password: String, @SerializedName("player_gold") var gold:Int)
{
}