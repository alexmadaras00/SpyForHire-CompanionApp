package com.example.spyforhire

import com.google.gson.annotations.SerializedName

data class SendItem(@SerializedName("weapon_level") var level:Int, @SerializedName("weapon_id") val id:Int,@SerializedName("weapon_name") val name:String)
{

}
