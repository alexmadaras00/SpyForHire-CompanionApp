package com.example.spyforhire

import `mipmap-xxhdpi`.WeaponHome
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home_screen.*


class HomeScreen : androidx.fragment.app.Fragment(R.layout.fragment_home_screen) {
    var itList=ArrayList<WeaponHome>()
    var mList=ArrayList<CardView>()
    var created:Boolean?=false
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(created==false) {
            itList.add(WeaponHome("pistol", R.drawable.pistol, 100))
            itList.add(WeaponHome("rifle", R.drawable.rifle, 200))
            itList.add(WeaponHome("shotgun", R.drawable.sniper_rifle, 400))
            mList.add(
                    CardView(
                            "Go to Big Ben, take a photo of it and then write when it was built and by who.",
                            250,
                            0,
                            R.drawable.monetization_on_24px,
                            0,
                            R.id.bar1,
                            false,
                            0.0,0.0,"da"
                    )
            )
            mList.add(
                    CardView(
                            "Go to Eiffel Tower, take a photo of it and then write when it was built and by who.",
                            500,
                            0,
                            R.drawable.monetization_on_24px,
                            0,
                            R.id.bar1,
                            false,
                            0.0,0.0,"da"
                    )
            )
            created=true
        }
        else
        {
            itList[0]
            itList[1]
            itList[2]
            mList
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                activity?.let{
                    view?.findViewById<ImageView>(R.id.settings)?.setOnClickListener {
                        val intent = Intent (activity, Settings::class.java)
                        startActivity(intent)
                    }
                    view?.findViewById<ImageView>(R.id.trophy)?.setOnClickListener {
                        val intent = Intent(activity, Achievements::class.java)
                        startActivity(intent)

                    }
                var recView=view.findViewById<RecyclerView>(R.id.store)
                if(recView!=null)
                    recView.adapter=WeaponAdapter(itList,object : WeaponAdapter.OnClickListener{
                        override fun onItemClick(position: Int) {
                            (context as MainActivity).changeFragment(StoreFragment())
                            (context as MainActivity).bot_navigation.selectedItemId = R.id.store_nav


                        }
                    })
                    if(recView!=null)
                        recView.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                    mis_home.layoutManager = LinearLayoutManager(context)
                    mis_home.adapter = Adapter(mList, object : Adapter.OnClickListener {
                        override fun onItemClick(position: Int) {
                            Log.i(TAG, "mission $position")
                            (context as MainActivity).changeFragment(MissionsScreen())
                            (context as MainActivity).bot_navigation.selectedItemId = R.id.missions_nav

                        }

                    })
        }

    }



}






