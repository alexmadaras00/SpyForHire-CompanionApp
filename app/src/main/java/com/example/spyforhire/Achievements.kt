package com.example.spyforhire

import android.content.Intent
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_achievements.*
import kotlinx.android.synthetic.main.activity_settings.*

class Achievements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)
        val vol=intent.getBooleanExtra("volume",true)
        if(vol==false) {
            mute()

        }
        else

            unmute()
        findViewById<ImageView>(R.id.imageView13).setOnClickListener {
            onBackPressed()
        }
        findViewById<ImageView>(R.id.settings3)?.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

       val pB= findViewById<ProgressBar>(R.id.progressBar2)
        pB.progress = 0
        val v=ArrayList<CardView>()
        val b=ArrayList<Boolean>()
        val img=ArrayList<ImageView>()
        val cImg=ArrayList<Int>()
        img.add(image_ach)
        img.add(image2_ach)
        img.add(image3_ach)
        img.add(image4_ach)
   /*     b.set(1,true)
        b.set(2,true)
        for(card in v)
        {
            b.add(false)
            v.add(card_ach)
            v.add(card2_ach)
            v.add(card3_ach)
            v.add(card4_ach)
        }
        for(i in 0..3)
        {
            if(b.get(i))
            {
                progressBar2.progress += 25
                percentage.text=progressBar2.progress.toString()
            }
        }

*/

    }
    @Override
    override fun onBackPressed() {
        super.onBackPressed()
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