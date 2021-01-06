package com.example.spyforhire

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vol=intent.getBooleanExtra("volume",true)
        if(vol==false)
            mute()
        else
            unmute()

        val time=intent.getIntExtra("timeback",0)
        Log.i(TAG,"$time")
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bot_navigation)
        val frag_Store=StoreFragment()
        val frag_home=HomeScreen()
        val miss_frag=MissionsScreen()

        changeFragment(frag_home)

        bottomNavigationView.setOnNavigationItemSelectedListener{
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