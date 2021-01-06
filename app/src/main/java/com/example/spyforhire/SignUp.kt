package com.example.spyforhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.spyforhire.ui.login.LoginActivity

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val user=findViewById<TextView>(R.id.email_resend).text
        val pass=findViewById<TextView>(R.id.password).text
        findViewById<Button>(R.id.reset).setOnClickListener {
            val i=Intent(this,LoginActivity::class.java)
            i.putExtra("user",user)
            i.putExtra("pass",pass)
            startActivity(i)
        }
    }
}