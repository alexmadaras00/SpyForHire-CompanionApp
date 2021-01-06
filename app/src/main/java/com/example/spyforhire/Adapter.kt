package com.example.spyforhire

import android.annotation.SuppressLint
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
        return list.size
    }
    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {

            itemView.findViewById<TextView>(R.id.textView6).text= list[position].t
            itemView.findViewById<TextView>(R.id.cash_2).text= list[position].cash.toString()
            itemView.findViewById<ProgressBar>(R.id.bar1).progress=list[position].bar
            itemView.findViewById<ImageView>(R.id.coins3).setImageResource(list[position].image)
            itemView.findViewById<TextView>(R.id.textul2).text= list[position].ready.toString()
            itemView.findViewById<ProgressBar>(R.id.bar1).background=list[position].progressBar.toDrawable()
            if(list[position].bar==100)
            {
                list[position].ready=1

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








