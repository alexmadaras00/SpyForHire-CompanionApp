package com.example.spyforhire.ui.login


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.spyforhire.*
import com.example.spyforhire.MainActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable


class LoginActivity : AppCompatActivity(),Serializable {
    var userIDDouble:Int =0
    lateinit var userName:String
    lateinit var userPass:String
    var coins:Int=0

    var compositeDisposable = CompositeDisposable()
    var l = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val u = intent.getStringExtra("user")
        val p = intent.getStringExtra("pass")
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        val loginB = findViewById<Button>(R.id.signup)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val user = intent.getStringExtra("user")
        val pass = intent.getStringExtra("pass")

        Log.i(com.example.spyforhire.TAG, "Info: $user, $pass")
        loginB.setOnClickListener {
            if (findViewById<EditText>(R.id.username).text.toString() != null && findViewById<EditText>(R.id.password).text.toString() != null) {
                val newUser = User(Global.id, findViewById<EditText>(R.id.username).text.toString(), findViewById<EditText>(R.id.password).text.toString(), Global.coins)

                newUser.gold = Global.coins
                newUser.id = Global.id
                login(newUser)
            }
            else toast("The fields must be filled")
        }
        findViewById<TextView>(R.id.forgot).setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)

        }

    }

    override fun onBackPressed() {
        Toast.makeText(
            applicationContext,
            "Cannot go back from the current screen!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun updateUiWithUser(model: String) {
        val welcome = "Welcome"
        // TODO : initiate successful logged in experience
        val username = findViewById<EditText>(R.id.username).text.toString()

        Toast.makeText(
            applicationContext,
            "$welcome,$username!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@SuppressLint("SupportAnnotationUsage") @StringRes errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
    fun login(user: User) {
        // PHONE: http://192.168.1.111:2020/
        // EMULATOR: http://10.0.2.2:2020/
        val retrofitClient = Client
            .getRetrofitInstance("http://10.0.2.2:2020/")
        val endpoint =retrofitClient.create(Routes::class.java)
        println(user)
        endpoint.newUser(user).enqueue(
            object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {

                    if(response.isSuccessful && response.body()!=null) {
                        toast("Login Successful")
                        userIDDouble = response.body()!!.id
                        userName = response.body()!!.username
                        userPass = response.body()!!.password
                        coins = response.body()!!.gold

                        println("User: $userName, Password: $userPass ,Coins: $coins")

                        //PrefUtil.globalID = userIDDouble;
                        //println("Login :" + PrefUtil.globalID)

                        Global.id = userIDDouble
                        if (coins != null)
                            Global.coins = coins
                        //println(MainActivity.globalID)
                        //SET USERID FOR FUTURE POSTS & GETS
                        if (userName != null) {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.putExtra("user", userName)
                            intent.putExtra("gold", coins)
                            startActivity(intent)
                        }
                    }
                    else if(response.code()==401) toast("Session expired! Try again!")
                    else toast("Wrong Input!")
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    toast(t.message.toString())
                }
            }
        )

    }
    private fun toast(string: String) {
        val applicationContext = this
        Toast.makeText(
            applicationContext,
            string,
            Toast.LENGTH_LONG
        ).show()
    }




    override fun onStop() {


        super.onStop()
    }

    override fun onDestroy() {


        super.onDestroy()
    }

    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */

}

