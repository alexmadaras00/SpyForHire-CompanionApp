package com.example.spyforhire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.spyforhire.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val user=email_resend.text
        val pass=password.text
        val conf=password3.text
        findViewById<Button>(R.id.reset).setOnClickListener {
            if(conf.toString()==pass.toString()) {
                val i = Intent(this, LoginActivity::class.java)
                i.putExtra("user", user)
                i.putExtra("pass", pass)
                startActivity(i)
            }
            else Toast.makeText(this,"The 2 password does not match",Toast.LENGTH_SHORT).show()
        }
    }
}