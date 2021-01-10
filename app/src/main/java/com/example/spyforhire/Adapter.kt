package com.example.spyforhire

import android.annotation.SuppressLint
import android.graphics.Color.red
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView

class Adapter(private var list: ArrayList<CardView>, val onClickListener: OnClickListener):
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    interface OnClickListener
    {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {

                return list.size;



    }
    @SuppressLint("CutPasteId", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {

            itemView.findViewById<TextView>(R.id.textView6).text= list[position].t
            itemView.findViewById<ProgressBar>(R.id.bar1).progress=list[position].bar
            itemView.findViewById<TextView>(R.id.cash_2).text= list[position].cash.toString()
            itemView.findViewById<ProgressBar>(R.id.bar1).background=list[position].progressBar.toDrawable()
            if(list[position].bar==100)
            {
                list[position].ready=1
                list[position].cash=0
                itemView.findViewById<TextView>(R.id.cash_2).setLinkTextColor(R.color.red)
                itemView.findViewById<TextView>(R.id.cash_2).setLinkTextColor(R.color.red)
                list[position].latitude
                list[position].longitude
                list[position].image=R.drawable.red_coin
                itemView.findViewById<ImageView>(R.id.coins3).setImageResource(list[position].image)
            }
            else
            {
                itemView.findViewById<ImageView>(R.id.coins3).setImageResource(list[position].image)
                itemView.findViewById<TextView>(R.id.textul2).text= list[position].ready.toString()
            }
            if(list[position].ready==1)
            {
                itemView.findViewById<TextView>(R.id.textView11).isInvisible=true
                itemView.findViewById<TextView>(R.id.textul2).isInvisible=true
                itemView.findViewById<TextView>(R.id.done).isInvisible=false
            }
            holder.itemView.setOnClickListener {
                onClickListener.onItemClick(position)
            }
          }

        }
    }








