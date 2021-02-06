package com.example.spyforhire

import android.content.Intent
import com.example.spyforhire.ui.login.Client
import com.example.spyforhire.ui.login.Routes
import com.example.spyforhire.ui.login.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Global {
    companion object Global {
        var volume: Boolean = true
        var coins: Int = 450
        var levelPistol:Int=0
        var levelSMG:Int=0
        var levelRifle:Int=0
        var levelSniper:Int=0

        var not: Boolean = true
        var on: Boolean = false
        var id: Int = 0

        lateinit var l: ArrayList<CardView>
    }
}
