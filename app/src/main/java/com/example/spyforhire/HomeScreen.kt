package com.example.spyforhire

import `mipmap-xxhdpi`.WeaponHome
import android.content.Intent
import android.net.wifi.p2p.nsd.WifiP2pUpnpServiceRequest.newInstance
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ErrorDialogFragment.newInstance
import kotlinx.android.synthetic.main.activity_main.*
import org.xmlpull.v1.XmlPullParserFactory.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance
import javax.xml.parsers.DocumentBuilderFactory.newInstance


class HomeScreen : Fragment(R.layout.fragment_home_screen) {
    var itList=ArrayList<WeaponHome>()
    var created:Boolean?=false
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(created==false) {
            itList.add(WeaponHome("pistol", R.drawable.pistol, 100))
            itList.add(WeaponHome("rifle", R.drawable.rifle, 200))
            itList.add(WeaponHome("shotgun", R.drawable.sniper_rifle, 400))
            created=true
        }
        else
        {
            itList[0]
            itList[1]
            itList[2]
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
                var recyclerView=view.findViewById<RecyclerView>(R.id.store)
                if(recyclerView!=null)
                    recyclerView.adapter=WeaponAdapter(itList,object : WeaponAdapter.OnClickListener{
                        override fun onItemClick(position: Int) {
                            (context as MainActivity).changeFragment(StoreFragment())
                            (context as MainActivity).bot_navigation.selectedItemId = R.id.store_nav;


                        }
                    })
                    if(recyclerView!=null)
                        recyclerView.layoutManager=LinearLayoutManager(this.activity,LinearLayoutManager.HORIZONTAL,false)

        }







    }



}






