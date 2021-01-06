package com.example.spyforhire

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.spyforhire.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.Serializable


class Settings : AppCompatActivity(),Serializable{
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mute()

            }
            else
            {
                unmute()
            }
        }
        findViewById<ImageView>(R.id.imageView2).setOnClickListener {
            val i=Intent(this, MainActivity::class.java)
            val time=getIntent().getIntExtra("time", 0)
            Log.i(TAG, "$time")
            i.putExtra("check",switch1.isChecked)
            i.putExtra("timeback", time)
            startActivity(i)
        }
        findViewById<ImageView>(R.id.imageView10).setOnClickListener {
            val intent=Intent(this, LoginActivity::class.java)
            intent.putExtra("check",switch1.isChecked)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.trophy3).setOnClickListener {
            val intent=Intent(this, Achievements::class.java)
            intent.putExtra("check",switch1.isChecked)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.imageView12).setOnClickListener {
            val url = "https://www.instagram.com/alexmadaras00/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }




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