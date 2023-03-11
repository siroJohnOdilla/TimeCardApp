package com.kronos.timecardapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter4(private val mList: List<ItemViewModel4>) : RecyclerView.Adapter<CustomAdapter4.ViewHolder>(){
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design_4, parent, false)
        return ViewHolder(v, mListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val itemsViewModel = mList[position]

        holder.txtViewTitleProfile.text = itemsViewModel.title
        holder.txtViewInformationDisplay.text = itemsViewModel.info
        holder.cardViewProfileDisplay.tag = position

    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView){
        val txtViewTitleProfile: TextView = itemView.findViewById(R.id.txtViewTitleProfile)
        val txtViewInformationDisplay: TextView = itemView.findViewById(R.id.txtViewInformationDisplay)
        val cardViewProfileDisplay: CardView = itemView.findViewById(R.id.cardViewProfileDisplay)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
                when(val position = ItemView.tag){
                    0 -> {
                        Toast.makeText(ItemView.context,"ACCOUNT TYPE",Toast.LENGTH_SHORT).show()
                        /*val intent = Intent(ItemView.context,AccountTagEditActivity::class.java)
                        val passInformation = txtViewInformationDisplay.text.toString()

                        intent.putExtra("PassInformation",passInformation)
                        intent.putExtra("darkStatusBar", false)
                        ItemView.context.startActivity(intent)*/
                    }
                    1 -> {
                        Toast.makeText(ItemView.context,"NAME",Toast.LENGTH_SHORT).show()
                        /*val intent = Intent(ItemView.context,NameEditActivity::class.java)
                        val passInformation = txtViewInformationDisplay.text.toString()

                        intent.putExtra("PassInformation",passInformation)
                        intent.putExtra("darkStatusBar", false)
                        ItemView.context.startActivity(intent)*/
                    }
                    2 -> {
                        Toast.makeText(ItemView.context,"PERSONAL DETAILS",Toast.LENGTH_SHORT).show()
                        /*val intent = Intent(ItemView.context,PersonalDetailsEditActivity::class.java)
                        val passInformation = txtViewInformationDisplay.text.toString()

                        intent.putExtra("PassInformation",passInformation)
                        intent.putExtra("darkStatusBar", false)
                        ItemView.context.startActivity(intent)*/
                    }
                    3 -> {
                        Toast.makeText(ItemView.context,"COMPANY",Toast.LENGTH_SHORT).show()
                        /*val intent = Intent(ItemView.context,CompanyEditActivity::class.java)
                        val passInformation = txtViewInformationDisplay.text.toString()

                        intent.putExtra("PassInformation",passInformation)
                        intent.putExtra("darkStatusBar", false)
                        ItemView.context.startActivity(intent)*/
                    }
                    4 -> {
                        Toast.makeText(ItemView.context,"JOB DESCRIPTION",Toast.LENGTH_SHORT).show()
                        /*val intent = Intent(ItemView.context,JobDescriptionEditActivity::class.java)
                        val passInformation = txtViewInformationDisplay.text.toString()

                        intent.putExtra("PassInformation",passInformation)
                        intent.putExtra("darkStatusBar", false)
                        ItemView.context.startActivity(intent)*/
                    }
                    5 -> {
                        Toast.makeText(ItemView.context,"CONTACT INFORMATION",Toast.LENGTH_SHORT).show()
                        /*val intent = Intent(ItemView.context,ContactInformationEditActivity::class.java)
                        val passInformation = txtViewInformationDisplay.text.toString()

                        intent.putExtra("PassInformation",passInformation)
                        intent.putExtra("darkStatusBar", false)
                        ItemView.context.startActivity(intent)*/
                    }
                }
            }
        }
    }
    fun getRetrieveName(){
        val a = ProfileViewActivity()
    }
}