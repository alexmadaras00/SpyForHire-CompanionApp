package com.example.spyforhire

import com.google.gson.annotations.SerializedName

data class GetItem(@SerializedName("weapon_level") val level:Int, @SerializedName("player_id") val id:Int, @SerializedName("weapon_name") val name:String)
{
}
