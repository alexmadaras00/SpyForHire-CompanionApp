package com.example.spyforhire

import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spyforhire.Retrofit.ViewModel
import com.example.spyforhire.ui.login.Client
import com.example.spyforhire.ui.login.Routes
import kotlinx.android.synthetic.main.fragment_store.*
import layout.AdapterItem
import layout.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoreFragment : Fragment(R.layout.fragment_store) {
    var created:Boolean?=false
    var iList = ArrayList<Item>()
    var complete: Boolean = false
    var coins:Int?=null
    lateinit var model: ViewModel
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val retrofitClient = Client .getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
        val endpoint = retrofitClient.create(Routes::class.java)
        val item:SendItem= SendItem(Global.id,"Pistol",0)
        val callback = endpoint.sendItem(item)
        callback.enqueue(object: Callback<SendItem>{
            override fun onResponse(call: Call<SendItem>, response: Response<SendItem>) {
               Global.levelPistol=response.body()!!.level
                println("Level: ${response.body()!!.level}")
                if(Global.levelPistol!=null)
                    println("LEVEL: $Global.levelPistol")

            }

            override fun onFailure(call: Call<SendItem>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
            }


        })
        val retrofitClient2 = Client .getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
        val endpoint2 = retrofitClient2.create(Routes::class.java)
        val item2:SendItem= SendItem(Global.id,"Submachine Gun",0)
        val callback2 = endpoint2.sendItem(item2)
        callback2.enqueue(object: Callback<SendItem>{
            override fun onResponse(call: Call<SendItem>, response: Response<SendItem>) {
                Global.levelSMG=response.body()!!.level
                if(Global.levelSMG!=null)
                    println("LEVEL: $Global.levelSMG")

            }

            override fun onFailure(call: Call<SendItem>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
            }


        })
        val retrofitClient3 = Client .getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
        val endpoint3 = retrofitClient3.create(Routes::class.java)
        val item3:SendItem= SendItem(Global.id,"Assault Rifle",0)
        val callback3 = endpoint3.sendItem(item3)
        callback3.enqueue(object: Callback<SendItem>{
            override fun onResponse(call: Call<SendItem>, response: Response<SendItem>) {
                Global.levelRifle=response.body()!!.level
                if(Global.levelRifle!=null)
                    println("LEVEL: $Global.levelRifle")

            }

            override fun onFailure(call: Call<SendItem>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
            }


        })
        val retrofitClient4 = Client .getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
        val endpoint4 = retrofitClient4.create(Routes::class.java)
        val item4:SendItem= SendItem(Global.id,"Sniper Rifle",0)
        val callback4 = endpoint4.sendItem(item4)
        callback4.enqueue(object: Callback<SendItem>{
            override fun onResponse(call: Call<SendItem>, response: Response<SendItem>) {
                Global.levelSniper=response.body()!!.level
                if(Global.levelSniper!=null)
                    println(Global.levelSniper)

            }

            override fun onFailure(call: Call<SendItem>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
            }


        })

        if(created==false) {
            iList.add(Item(1,Global.id,R.drawable.pistol, "Pistol", 0, 10, false))
            iList.add(Item(3,Global.id,R.drawable.smg, "SMG", 0, 30, false))
            iList.add(Item(4,Global.id,R.drawable.rifle, "Rifle", 0, 40, false))
            iList.add(Item(2,Global.id,R.drawable.sniper_rifle, "Sniper", 0, 60, false))
            created=true
        }

        if(created==true) {
            iList[0]
            iList[1]
            iList[2]
            iList[3]
        }

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cash2.text=Global.coins.toString()
        activity?.let {
            view?.findViewById<ImageView>(R.id.settings2)?.setOnClickListener {
                val intent = Intent(activity, Settings::class.java)
                startActivity(intent)
            }
            view?.findViewById<ImageView>(R.id.trophy2)?.setOnClickListener {
                val intent = Intent(activity, Achievements::class.java)
                startActivity(intent)
            }
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
            if (recyclerView != null) {
                recyclerView.adapter = AdapterItem(iList)
                AdapterItem(iList).notifyDataSetChanged()
                cash2.text = Global.coins.toString()
                recyclerView.layoutManager = LinearLayoutManager(context)
            }

      /*    val retrofitClient = Client.getRetrofitInstance("http://10.0.2.2:3000/")
            val endpoint = retrofitClient.create(Routes::class.java)
            val callback = endpoint.getPets()
            callback.enqueue(object : Callback<List<Item>> {
                override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                    response.body()?.forEach()
                    {
                        iList.add(Item(it.image, it.name, it.level, it.coin, it.complete))
                    }

                }


            })

       */
        }
    }
    private fun mute() {
        //mute audio
        val amanager = activity?.getSystemService(AppCompatActivity.AUDIO_SERVICE) as AudioManager
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true)
    }

    fun unmute() {
        //unmute audio
        val amanager = activity?.getSystemService(AppCompatActivity.AUDIO_SERVICE) as AudioManager
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false)
    }

    override fun onResume() {
        super.onResume()
        cash2.text=Global.coins.toString()
        if (Global.volume == false)
            mute()
        else
            unmute()
    }

    override fun onStart() {

        cash2.text=Global.coins.toString()
        super.onStart()
    }

    }






