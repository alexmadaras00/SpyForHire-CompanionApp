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
import kotlin.properties.Delegates


class StoreFragment : Fragment(R.layout.fragment_store) {
    var created:Boolean?=false
    var iList = ArrayList<Item>()
    var complete: Boolean = false
    var coins:Int?=null
    var lpistol:Int = 0
    var lsmg:Int = 0
    var lrifle:Int = 0
    var lsniper:Int = 0

    lateinit var model: ViewModel
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if(Global.create==false) {
        val retrofitClient = Client.getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
        val endpoint= retrofitClient.create(Routes::class.java)
        val item:SendItem= SendItem(Global.id,"Pistol",Global.levelPistol)
        println("ID: ${Global.id}")
        val callback = endpoint.sendItem(item)
        callback.enqueue(object: Callback<SendItem>{
            override fun onResponse(call: Call<SendItem>, response: Response<SendItem>) {
                Global.levelPistol=response.body()!!.level
                println("SMG:${response.body()!!.level}")
                if(Global.levelPistol!=null)
                    println("Var levelPistol: ${Global.levelPistol}")

            }

            override fun onFailure(call: Call<SendItem>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
                println("Here")
            }


        })
        val retrofitClient2 = Client.getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
        val endpoint2 = retrofitClient2.create(Routes::class.java)
        val item2:SendItem= SendItem(Global.id,"Submachine Gun",Global.levelSMG)
        println("ID: ${Global.id}")
        val callback2 = endpoint2.sendItem(item2)
        callback2.enqueue(object: Callback<SendItem>{
            override fun onResponse(call: Call<SendItem>, response: Response<SendItem>) {
                Global.levelSMG=response.body()!!.level
                println("SMG:${response.body()!!.level}")
                if(Global.levelSMG!=null)
                    println("Var levelSMG: ${Global.levelSMG}")

            }

            override fun onFailure(call: Call<SendItem>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
                println("Here")
            }


        })
        val retrofitClient3 = Client .getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
        val endpoint3 = retrofitClient3.create(Routes::class.java)
        val item3:SendItem= SendItem(Global.id,"Assault Rifle",Global.levelRifle)
        val callback3 = endpoint3.sendItem(item3)
        callback3.enqueue(object: Callback<SendItem>{
            override fun onResponse(call: Call<SendItem>, response: Response<SendItem>) {
                Global.levelRifle=response.body()!!.level


                println("Var levelRifle: ${Global.levelRifle}")

            }

            override fun onFailure(call: Call<SendItem>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
                println("Here")
            }


        })
        val retrofitClient4 = Client .getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
        val endpoint4 = retrofitClient4.create(Routes::class.java)
        val item4:SendItem= SendItem(Global.id,"Sniper Rifle",Global.levelSniper)
        val callback4 = endpoint4.sendItem(item4)
        callback4.enqueue(object: Callback<SendItem>{
            override fun onResponse(call: Call<SendItem>, response: Response<SendItem>) {
                Global.levelSniper=response.body()!!.level


                println("Var levelSniper: ${Global.levelSniper}")
                lsniper=Global.levelSniper

            }

            override fun onFailure(call: Call<SendItem>, t: Throwable) {
                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
                println("Here")
            }
        })


            iList.add(Item(1,Global.id,R.drawable.pistol, "Pistol", Global.levelPistol, 10, false))
            iList.add(Item(3,Global.id,R.drawable.smg, "Submachine Gun", Global.levelSMG, 30, false))
            iList.add(Item(4,Global.id,R.drawable.rifle, "Assault Rifle", Global.levelRifle, 40, false))
            iList.add(Item(2,Global.id,R.drawable.sniper_rifle, "Sniper Rifle", Global.levelSniper, 60, false))
            Global.create=true
        }

        if(Global.create==true) {
            iList[0].level=Global.levelPistol
            iList[1].level=Global.levelSMG
            iList[2].level=Global.levelRifle
            iList[3].level=Global.levelSniper
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
        StoreFragment().activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        cash2.text=Global.coins.toString()
        if (Global.volume == false)
            mute()
        else
            unmute()

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        if (recyclerView != null) {
            recyclerView.adapter = AdapterItem(iList)
            AdapterItem(iList).notifyDataSetChanged()

            cash2.text = Global.coins.toString()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        StoreFragment().activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
    }

    override fun onStart() {
        StoreFragment().activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        cash2.text=Global.coins.toString()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        if (recyclerView != null) {
            recyclerView.adapter = AdapterItem(iList)
            AdapterItem(iList).notifyDataSetChanged()
            cash2.text = Global.coins.toString()
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        StoreFragment().activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        StoreFragment().activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        super.onStart()
    }

    }






