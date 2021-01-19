package layout

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.spyforhire.*
import com.example.spyforhire.Adapter
import com.example.spyforhire.R.id.cash2
import java.lang.Override as Override

class AdapterItem(private var list: ArrayList<Item>):
    RecyclerView.Adapter<AdapterItem.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.card_view_items,parent,false)

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
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {


        holder.apply {
            itemView.findViewById<ImageView>(R.id.imageView18).setImageResource(list[position].image)
            itemView.findViewById<TextView>(R.id.textView10).text= list[position].name

            itemView.findViewById<TextView>(R.id.cash_2).text= list[position].coin.toString()
            list[position].complete=false
            if (list[position].level == 1) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1


            }

            else if (list[position].level == 2) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar2)!!.progress = 1


            }
            else if (list[position].level ==3) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar2)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar3)!!.progress = 1


            }
            else if (list[position].level == 4) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar2)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar3)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar4)!!.progress = 1


            }
            else if (list[position].level == 5) {
                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar2)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar3)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar4)!!.progress = 1
                itemView.findViewById<ProgressBar>(R.id.bar5)!!.progress = 1
                itemView.findViewById<Button>(R.id.buy).isInvisible=true
                itemView.findViewById<Button>(R.id.buy).isEnabled=false
                itemView.findViewById<ImageView>(R.id.imageView19).isInvisible=false
                itemView.findViewById<TextView>(R.id.textView23).isInvisible=false
                list[position].complete=true


            }
            var coins:Int=(cash2).toString().toInt()
            var price:Int=list[position].coin.toString().toInt()
            itemView.findViewById<Button>(R.id.buy)?.setOnClickListener {
                        if(coins>0 && coins>price) {
                            if (list[position].level == 0) {
                                itemView.findViewById<ProgressBar>(R.id.bar1)!!.progress = 1
                                list[position].level += 1
                                MySingleton.coins -= price
                            } else if (list[position].level == 1) {

                                itemView.findViewById<ProgressBar>(R.id.bar2)!!.progress = 1
                                list[position].level += 1
                                MySingleton.coins -= price
                            } else if (list[position].level == 2) {
                                itemView.findViewById<ProgressBar>(R.id.bar3)!!.progress = 1
                                list[position].level += 1
                                MySingleton.coins -= price

                            } else if (list[position].level == 3) {
                                itemView.findViewById<ProgressBar>(R.id.bar4)!!.progress = 1
                                list[position].level += 1
                                MySingleton.coins -= price

                            } else if (list[position].level == 4) {
                                itemView.findViewById<ProgressBar>(R.id.bar5)!!.progress = 1
                                list[position].level += 1
                                list[position].complete = true
                                itemView.findViewById<Button>(R.id.buy).isInvisible = true
                                itemView.findViewById<ImageView>(R.id.imageView19).isInvisible =
                                    false
                                itemView.findViewById<TextView>(R.id.textView23).isInvisible = false
                                MySingleton.coins -= price


                            }
                        }
                        else {
                            MySingleton.coins=0
                            }
                        }
                    }
                }
            }






