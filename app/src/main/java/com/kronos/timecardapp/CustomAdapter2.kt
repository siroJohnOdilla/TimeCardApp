package com.kronos.timecardapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter2(private val mList: List<ItemViewModel2>) : RecyclerView.Adapter<CustomAdapter2.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design_2, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val itemsViewModel = mList[position]

        holder.txtViewTimeAttendanceDate.text = itemsViewModel.date
        holder.txtViewTimeAttendanceName.text = itemsViewModel.name
        holder.txtViewTimeAttendanceSiteBranch.text = itemsViewModel.officeSiteBranch
        holder.txtViewTimeAttendanceDepartment.text = itemsViewModel.department
        holder.txtViewTimeAttendanceJobTitle.text = itemsViewModel.jobTitle
        holder.txtViewTimeAttendanceTimeIn.text = itemsViewModel.timeIn
        holder.txtViewTimeAttendanceTimeOut.text = itemsViewModel.timeOut
        holder.txtViewTimeAttendanceDateTimeWorked.text = itemsViewModel.timeWorked
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val txtViewTimeAttendanceDate: TextView = itemView.findViewById(R.id.txtViewTimeAttendanceDate)
        val txtViewTimeAttendanceName: TextView = itemView.findViewById(R.id.txtViewTimeAttendanceName)
        val txtViewTimeAttendanceSiteBranch: TextView = itemView.findViewById(R.id.txtViewTimeAttendanceSiteBranch)
        val txtViewTimeAttendanceDepartment: TextView = itemView.findViewById(R.id.txtViewTimeAttendanceDepartment)
        val txtViewTimeAttendanceJobTitle: TextView = itemView.findViewById(R.id.txtViewTimeAttendanceJobTitle)
        val txtViewTimeAttendanceTimeIn: TextView = itemView.findViewById(R.id.txtViewTimeAttendanceTimeIn)
        val txtViewTimeAttendanceTimeOut: TextView = itemView.findViewById(R.id.txtViewTimeAttendanceTimeOut)
        val txtViewTimeAttendanceDateTimeWorked: TextView = itemView.findViewById(R.id.txtViewTimeAttendanceTimeWorked)
    }
}