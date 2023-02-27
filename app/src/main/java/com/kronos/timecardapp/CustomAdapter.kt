package com.kronos.timecardapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val itemsViewModel = mList[position]

        holder.txtViewNameProfile.text = itemsViewModel.name
        holder.txtViewJobTitleProfile.text = itemsViewModel.jobTitle
        holder.txtViewOfficeSiteBranchProfile.text = itemsViewModel.officeSiteBranch
        holder.txtViewNationalIdProfile.text = itemsViewModel.idNo
        holder.txtViewAccountTagProfile.text = itemsViewModel.accountTag
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val txtViewNameProfile: TextView = itemView.findViewById(R.id.txtViewNameProfile)
        val txtViewJobTitleProfile: TextView = itemView.findViewById(R.id.txtViewJobTitleProfile)
        val txtViewOfficeSiteBranchProfile: TextView = itemView.findViewById(R.id.txtViewOfficeSiteBranchProfile)
        val txtViewNationalIdProfile: TextView = itemView.findViewById(R.id.txtViewNationalIdProfile)
        val txtViewAccountTagProfile: TextView = itemView.findViewById(R.id.txtViewAccountTagProfile)
    }
}