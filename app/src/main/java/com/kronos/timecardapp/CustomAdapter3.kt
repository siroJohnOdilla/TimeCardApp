package com.kronos.timecardapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter3(private val mList: List<ItemViewModel3>) : RecyclerView.Adapter<CustomAdapter3.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design_3, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val itemsViewModel = mList[position]

        holder.txtViewLeaveScheduleName.text = itemsViewModel.name
        holder.txtViewLeaveScheduleLeave.text = itemsViewModel.leave
        holder.txtViewLeaveScheduleStartDate.text = itemsViewModel.startDate
        holder.txtViewLeaveScheduleEndDate.text = itemsViewModel.endDate
        holder.txtViewLeaveScheduleNoOfDays.text = itemsViewModel.noOfDays
        holder.txtViewLeaveScheduleReliever.text = itemsViewModel.reliever
        holder.txtViewLeaveScheduleAuthorizedBy.text = itemsViewModel.authorizedBy
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val txtViewLeaveScheduleName: TextView = itemView.findViewById(R.id.txtViewLeaveScheduleName)
        val txtViewLeaveScheduleLeave: TextView = itemView.findViewById(R.id.txtViewLeaveScheduleLeave)
        val txtViewLeaveScheduleStartDate: TextView = itemView.findViewById(R.id.txtViewLeaveScheduleStartDate)
        val txtViewLeaveScheduleEndDate: TextView = itemView.findViewById(R.id.txtViewLeaveScheduleEndDate)
        val txtViewLeaveScheduleNoOfDays: TextView = itemView.findViewById(R.id.txtViewLeaveScheduleNoOfDays)
        val txtViewLeaveScheduleReliever: TextView = itemView.findViewById(R.id.txtViewLeaveScheduleReliever)
        val txtViewLeaveScheduleAuthorizedBy: TextView = itemView.findViewById(R.id.txtViewLeaveScheduleAuthorizedBy)
    }
}