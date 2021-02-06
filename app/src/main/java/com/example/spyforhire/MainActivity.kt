package com.example.spyforhire

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.spyforhire.ui.login.Client
import com.example.spyforhire.ui.login.Routes
import com.example.spyforhire.ui.login.User
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    val frag_Store=StoreFragment()
    val frag_home=HomeScreen()
    val miss_frag=MissionsScreen()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vol=intent.getBooleanExtra("volume",true)
        if(intent.extras!=null)
        {
            val user=intent.getStringExtra("data")
            Toast.makeText(this,"Welcome, $user!",Toast.LENGTH_SHORT).show()
        }
        val time=intent.getIntExtra("timeback",0)
        Log.i(TAG,"$time")
        changeFragment(frag_home)
        bot_navigation.setOnNavigationItemSelectedListener{
                item ->when(item.itemId) {
                R.id.home_nav -> changeFragment(frag_home)


                R.id.store_nav->{
                    changeFragment(frag_Store)

                }

                R.id.missions_nav->{
                    changeFragment(miss_frag)

                        }
                }
            true

        }


       /* findViewById<RecyclerView>(R.id.recyclerView).adapter=adaptor*/



    }
    fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.beginTransaction()
            replace(R.id.fragm_main,fragment)
            commit()
        }
    }

    override fun onResume() {
        if(Global.volume==false)
            mute()
        else
            unmute()
        super.onResume()
    }
    fun startSound() {
        var mp = MediaPlayer.create(applicationContext, R.raw.countdown)
        mp.start()
        mp.setVolume(100f,100f)
    }
    fun stopSound() {
        var mp = MediaPlayer.create(applicationContext, R.raw.countdown)
        mp.stop()

    }
    private fun mute() {
        //mute audio
        val amanager = getSystemService(AUDIO_SERVICE) as AudioManager
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true)
    }

    fun unmute() {
        //unmute audio
        val amanager = getSystemService(AUDIO_SERVICE) as AudioManager
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false)
    }


}
