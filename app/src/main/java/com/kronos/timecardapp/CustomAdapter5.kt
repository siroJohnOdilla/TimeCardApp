package com.kronos.timecardapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CustomAdapter5(private val mList: List<ItemViewModel5>) : RecyclerView.Adapter<CustomAdapter5.ViewHolder>(){
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design_5, parent, false)
        return ViewHolder(v, mListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val itemsViewModel = mList[position]

        holder.txtViewVisitorBook.text = itemsViewModel.date
        holder.txtViewNameVisitorBook.text = itemsViewModel.name
        holder.txtViewNationalIdVisitorBook.text = itemsViewModel.nationalId
        holder.txtViewTelephoneVisitorBook.text = itemsViewModel.telephoneNumber
        holder.txtViewCompanyVisitorBook.text = itemsViewModel.company
        holder.txtViewHostVisitorBook.text = itemsViewModel.companyHost
        holder.txtViewReasonVisitorBook.text = itemsViewModel.reasonVisit
        holder.txtViewTimeInVisitorBook.text = itemsViewModel.timeIn
        holder.txtViewAuthorizeTimeInVisitorBook.text = itemsViewModel.authorizedTimeIn
        holder.txtViewTimeOutVisitorBook.text = itemsViewModel.timeOut
        holder.txtViewAuthorizeTimeOutVisitorBook.text = itemsViewModel.authorizedTimeOut
        holder.cardViewVisitorBook.tag = position

    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView){
        val cardViewVisitorBook: CardView = itemView.findViewById(R.id.cardViewVisitorBook)
        val txtViewVisitorBook: TextView = itemView.findViewById(R.id.txtViewVisitorBook)
        val txtViewNameVisitorBook: TextView = itemView.findViewById(R.id.txtViewNameVisitorBook)
        val txtViewNationalIdVisitorBook: TextView = itemView.findViewById(R.id.txtViewNationalIdVisitorBook)
        val txtViewTelephoneVisitorBook: TextView = itemView.findViewById(R.id.txtViewTelephoneVisitorBook)
        val txtViewCompanyVisitorBook: TextView = itemView.findViewById(R.id.txtViewCompanyVisitorBook)
        val txtViewHostVisitorBook: TextView = itemView.findViewById(R.id.txtViewHostVisitorBook)
        val txtViewReasonVisitorBook: TextView = itemView.findViewById(R.id.txtViewReasonVisitorBook)
        val txtViewTimeInVisitorBook: TextView = itemView.findViewById(R.id.txtViewTimeInVisitorBook)
        val txtViewAuthorizeTimeInVisitorBook: TextView = itemView.findViewById(R.id.txtViewAuthorizeTimeInVisitorBook)
        val txtViewTimeOutVisitorBook: TextView = itemView.findViewById(R.id.txtViewTimeOutVisitorBook)
        val txtViewAuthorizeTimeOutVisitorBook: TextView = itemView.findViewById(R.id.txtViewAuthorizeTimeOutVisitorBook)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
                when(val position = ItemView.tag){
                    position -> {
                        if(txtViewTimeOutVisitorBook.text.toString().isNotEmpty()){
                            Toast.makeText(ItemView.context,"YOU HAVE ALREADY SIGNED OUT",Toast.LENGTH_SHORT).show()
                        } else{
                            val name = txtViewNameVisitorBook.text.toString()
                            val db = DBHelper4(ItemView.context, null)
                            val cursor = db.getDetails()

                            if(cursor!!.moveToFirst()){
                                do{
                                    val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NAME_COL))
                                    val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.ID_COL))
                                    val authorizedTimeInPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.AUTHORIZED_TIME_IN))

                                    if(name == namePrint.toString()){
                                        val id = idPrint.toLong()
                                        val makeDateFormat2 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                        val timeOut = makeDateFormat2.format(Date())

                                        val authorizedTimeOut = authorizedTimeInPrint.toString()

                                        db.updateVisitor(id, timeOut, authorizedTimeOut)

                                        Toast.makeText(ItemView.context,"GOOD BYE $name\nTIME OUT: $timeOut",Toast.LENGTH_SHORT).show()
                                    }

                                } while(cursor.moveToNext())
                            }
                            cursor.close()
                        }
                    }
                }
            }
        }
    }
}