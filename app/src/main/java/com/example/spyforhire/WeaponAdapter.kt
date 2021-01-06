package com.example.spyforhire

import `mipmap-xxhdpi`.WeaponHome
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView

class WeaponAdapter(private  var list:ArrayList<WeaponHome>, var onClickListener: OnClickListener):RecyclerView.Adapter<WeaponAdapter.ViewHolder>()
{
inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)
    interface OnClickListener
    {
        fun onItemClick(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view=LayoutInflater.from(parent.context).inflate(R.layout.weapon_card_home,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.apply {
          itemView.findViewById<ImageView>(R.id.pistol).setImageResource(list[position].src)
          itemView.findViewById<TextView>(R.id.amount).text=list[position].amount.toString()


      }
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}