package com.example.spyforhire

import android.content.Context
import kotlin.properties.Delegates

class Global {
    companion object Global {
         var context: Context? = null
        var volume: Boolean = true
        var create:Boolean=false
        var coins: Int = 0
        var levelPistol:Int=0
        var levelSMG:Int=0
        var levelRifle:Int = 0
        var levelSniper:Int = 0

        var not: Boolean = true
        var on: Boolean = false
        var id: Int = 0

        lateinit var l: ArrayList<CardView>
    }
}
