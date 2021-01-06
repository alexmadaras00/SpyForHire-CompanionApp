package com.example.spyforhire

import android.widget.TextView
import java.io.Serializable

data class CardView(
        var t: String, var cash: Int, var bar: Int, var image: Int, var ready:Int,
        var progressBar: Int, var completed: Boolean,var latitude:Double,var longitude:Double):Serializable
{

}