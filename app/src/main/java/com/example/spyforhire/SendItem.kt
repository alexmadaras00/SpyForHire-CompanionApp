package com.example.spyforhire

import com.google.gson.annotations.SerializedName

data class SendItem(@SerializedName("player_id") val id:Int,@SerializedName("weapon_name") val name:String,@SerializedName("weapon_level") var level:Int)
{

}
