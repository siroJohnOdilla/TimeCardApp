package com.kronos.timecardapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter7(private val mList: List<ItemsViewModel6>) : RecyclerView.Adapter<CustomAdapter7.ViewHolder>() {
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design_6, parent, false)
        return ViewHolder(v, mListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val itemsViewModel = mList[position]

        holder.recentClockOut.text = itemsViewModel.recentTimeOut
        holder.recentClockIn.text = itemsViewModel.recentTimeIn
        holder.recentTotalTime.text = itemsViewModel.recentTotalTime
        holder.recentDate.text = itemsViewModel.recentDate
        holder.cardProfile.tag = position
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView){
        val recentClockOut: TextView = itemView.findViewById(R.id.recentClockOut)
        val recentClockIn: TextView = itemView.findViewById(R.id.recentClockIn)
        val recentTotalTime: TextView = itemView.findViewById(R.id.recentTotalTime)
        val recentDate: TextView = itemView.findViewById(R.id.recentDate)
        val cardProfile: CardView = itemView.findViewById(R.id.cardViewRecent)

        init{
            listener.onItemClick(adapterPosition)
            when(val position = ItemView.tag){
                position -> {

                }
            }
        }
    }

}