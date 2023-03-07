package com.kronos.timecardapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(v, mListener)
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
    class ViewHolder(ItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView){
        val txtViewNameProfile: TextView = itemView.findViewById(R.id.txtViewNameProfile)
        val txtViewJobTitleProfile: TextView = itemView.findViewById(R.id.txtViewJobTitleProfile)
        val txtViewOfficeSiteBranchProfile: TextView = itemView.findViewById(R.id.txtViewOfficeSiteBranchProfile)
        val txtViewNationalIdProfile: TextView = itemView.findViewById(R.id.txtViewNationalIdProfile)
        val txtViewAccountTagProfile: TextView = itemView.findViewById(R.id.txtViewAccountTagProfile)
        val cardProfile: CardView = itemView.findViewById(R.id.cardProfile)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
