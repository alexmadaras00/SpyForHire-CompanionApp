package layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.spyforhire.*
import com.example.spyforhire.ui.login.Client
import com.example.spyforhire.ui.login.Routes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext


class AdapterItem(private var list: ArrayList<Item>):
    RecyclerView.Adapter<AdapterItem.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.card_view_items, parent, false)

        return ViewHolder(view)
    }
    init {
        setHasStableIds(true);
    }
    @Override
    override fun getItemCount(): Int
    {
        return list.size
    }
    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            itemView.findViewById<ImageView>(R.id.imageView18)
                .setImageResource(list[position].image)
            itemView.findViewById<TextView>(R.id.textView10).text = list[position].name

            itemView.findViewById<TextView>(R.id.cash_2).text = list[position].coin.toString()
            list[position].complete = false
            if (list[position].level == 0) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 0
                if (position == 0) {
                    Global.levelPistol = 0
                }
                if (position == 1) {
                    Global.levelSMG = 0
                }
                if (position == 2) {
                    Global.levelRifle = 0
                }
                if (position == 3) {
                    Global.levelSniper = 0
                }
            } else if (list[position].level == 1) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                if (position == 0) {
                    Global.levelPistol = 1
                }
                if (position == 1) {
                    Global.levelSMG = 1
                }
                if (position == 2) {
                    Global.levelRifle = 1
                }
                if (position == 3) {
                    Global.levelSniper = 1
                }
            } else if (list[position].level == 2) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar2)!!.progress = 1
                if (position == 0) {
                    Global.levelPistol = 2
                }
                if (position == 1) {
                    Global.levelSMG = 2
                }
                if (position == 2) {
                    Global.levelRifle = 2
                }
                if (position == 3) {
                    Global.levelSniper = 2
                }


            } else if (list[position].level == 3) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar2)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar3)!!.progress = 1

                list[position].complete = true
                if (position == 0) {
                    Global.levelPistol = 3
                }
                if (position == 1) {
                    Global.levelSMG = 3
                }
                if (position == 2) {
                    Global.levelRifle = 3
                }
                if (position == 3) {
                    Global.levelSniper = 3
                }
                itemView.findViewById<Button>(R.id.buy).isInvisible = true
                itemView.findViewById<Button>(R.id.buy).isEnabled = false
                itemView.findViewById<ImageView>(R.id.imageView19).isInvisible = false
                itemView.findViewById<TextView>(R.id.textView23).isInvisible = false
            }
            var price: Int = list[position].coin.toString().toInt()
            if (Global.create == true) {
                itemView.findViewById<Button>(R.id.buy)?.setOnClickListener {

                    if (Global.coins > 0 && Global.coins > price) {
                        if (list[position].level == 0) {
                            itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                            list[position].level += 1
                            Global.coins -= price
                            if (position == 0) {
                                Global.levelPistol++

                            }
                            if (position == 1)
                                Global.levelSMG++
                            if (position == 2)
                                Global.levelRifle++
                            if (position == 3)
                                Global.levelSniper++
                        } else if (list[position].level == 1) {

                            itemView.findViewById<ProgressBar>(R.id.bar2)!!.progress = 1
                            list[position].level += 1
                            Global.coins -= price
                            if (position == 0)
                                Global.levelPistol++
                            if (position == 1)
                                Global.levelSMG++
                            if (position == 2)
                                Global.levelRifle++
                            if (position == 3)
                                Global.levelSniper++
                        } else if (list[position].level == 2) {
                            itemView.findViewById<ProgressBar>(R.id.bar3)!!.progress = 1
                            list[position].level += 1
                            Global.coins -= price
                        } else if (list[position].level == 3) {
                            itemView.findViewById<Button>(R.id.buy).isInvisible = true
                            itemView.findViewById<Button>(R.id.buy).isEnabled = false
                            itemView.findViewById<ImageView>(R.id.imageView19).isInvisible = false
                            itemView.findViewById<TextView>(R.id.textView23).isInvisible = false
                            list[position].complete = true
                            if (position == 0) {
                                Global.levelPistol++
                                val retrofitClient5 =
                                    Client.getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
                                val endpoint5 = retrofitClient5.create(Routes::class.java)
                                val item5: SendItem =
                                    SendItem(Global.levelPistol,(position+1)* Global.id, "Pistol")

                                endpoint5.setLevel(item5)
                            }
                            if (position == 1) {
                                Global.levelSMG++
                                val retrofitClient6 =
                                    Client.getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
                                val endpoint6 = retrofitClient6.create(Routes::class.java)
                                val item6: SendItem =
                                    SendItem(Global.levelSMG, (position+1)*Global.id, "Submachine Gun")
                                println("ID: ${Global.id}")
                                endpoint6.setLevel(item6)
                            }
                            if (position == 2) {
                                Global.levelRifle++
                                val retrofitClient7 =
                                    Client.getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
                                val endpoint7 = retrofitClient7.create(Routes::class.java)
                                val item7: SendItem =
                                    SendItem(Global.levelRifle, (position+1)*Global.id, "Assault Rifle")
                                endpoint7.setLevel(item7)
                            }
                            if (position == 3) {
                                Global.levelSniper++
                                val retrofitClient8 =
                                    Client.getRetrofitInstance("http://10.0.2.2:2020/") //be carefull when using the emulator or the phone. If you are using the phone, make sure you are on the same LAN.
                                val endpoint8 = retrofitClient8.create(Routes::class.java)
                                val item8: SendItem =
                                    SendItem(Global.levelSniper,(position+1)*Global.id, "Sniper Rifle")
                                endpoint8.setLevel(item8)
                                println(item8)
                            }
                        }
                        else
                        {
                            Global.coins = 0
                        }
                        notifyDataSetChanged()
                    }
                }
            }
        }

      }
    }






