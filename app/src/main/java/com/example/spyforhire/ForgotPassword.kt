package com.example.spyforhire

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.spyforhire.ui.login.LoginActivity

class ForgotPassword : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    findViewById<Button>(R.id.signup).setOnClickListener {

        val handler = android.os.Handler()
        val runnable = Runnable {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        handler.postDelayed(runnable, 2000)
        Toast.makeText(this, "An e-mail has been sent to the address",Toast.LENGTH_LONG).show()


    }


    }
}