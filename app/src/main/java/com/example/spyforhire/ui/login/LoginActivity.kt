package com.example.spyforhire.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.activity.OnBackPressedCallback
import com.example.spyforhire.ForgotPassword
import com.example.spyforhire.MainActivity

import com.example.spyforhire.R
import com.example.spyforhire.SignUp
import java.io.Serializable

class LoginActivity : AppCompatActivity(),Serializable {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        val u="Alex"
        val p="1234"
        val username = findViewById<EditText>(R.id.email_resend).text
        val password = findViewById<EditText>(R.id.password).text
        val login = findViewById<Button>(R.id.reset)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val user = intent.getStringExtra("user")
        val pass = intent.getStringExtra("pass")
        login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            Log.i(com.example.spyforhire.TAG,"${user.toString()} , ${pass.toString()}")
            Log.i(com.example.spyforhire.TAG,"${u.toString()} , ${p.toString()}")
            if (username.toString() == u && password.toString() == p) {
                updateUiWithUser(username.toString())
                startActivity(intent)
            } else
            {
                Log.i(com.example.spyforhire.TAG,"no")
                showLoginFailed("Login failed! Try again!")
            }
        }
        findViewById<TextView>(R.id.textView15).setOnClickListener {

                val intent=Intent(this,SignUp::class.java)
                startActivity(intent)

        }
        findViewById<TextView>(R.id.forgot).setOnClickListener{
            val intent=Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext,"Cannot go back from the current screen!",Toast.LENGTH_LONG).show()
    }

    private fun updateUiWithUser(model: String) {
        val welcome = "Welcome"
        // TODO : initiate successful logged in experience
        val username = findViewById<EditText>(R.id.email_resend).text.toString()

        Toast.makeText(
                applicationContext,
                "$welcome,$username!",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@SuppressLint("SupportAnnotationUsage") @StringRes errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

}
