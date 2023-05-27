package com.kronos.timecardapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.telephony.SmsManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        holder.txtViewTimeOutVisitorBook.text = itemsViewModel.timeOut
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
        val txtViewTimeOutVisitorBook: TextView = itemView.findViewById(R.id.txtViewTimeOutVisitorBook)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
                when(val position = ItemView.tag){
                    position -> {
                        if(txtViewTimeOutVisitorBook.text.toString().isNotEmpty()){
                            Toast.makeText(ItemView.context,"YOU HAVE ALREADY SIGNED OUT",Toast.LENGTH_SHORT).show()
                        } else{
                            val dialog = MaterialAlertDialogBuilder(ItemView.context,R.style.RoundedMaterialDialog).setView(R.layout.dialog_profileaccess).show()

                            val txtViewDisplayMessageProfileAccess = dialog.findViewById<TextView>(R.id.txtViewDisplayMessageProfileAccess)
                            val displayName = txtViewNameVisitorBook.text.toString()
                            if (txtViewDisplayMessageProfileAccess != null) {
                                txtViewDisplayMessageProfileAccess.text = displayName
                            }

                            val editTxtEnterPINProfileAccess = dialog.findViewById<EditText>(R.id.editTxtEnterPINProfileAccess)

                            val btnCancelPINProfileAccess = dialog.findViewById<ImageView>(R.id.btnCancelPINProfileAccess)
                            if (btnCancelPINProfileAccess != null) {
                                btnCancelPINProfileAccess.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }
                            val btnEnterPINProfileAccess = dialog.findViewById<Button>(R.id.btnEnterPINProfileAccess)
                            if (btnEnterPINProfileAccess != null) {
                                btnEnterPINProfileAccess.setOnClickListener {
                                    val name = txtViewNameVisitorBook.text.toString()
                                    val code = editTxtEnterPINProfileAccess?.text.toString()

                                    val makeDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    val date = makeDateFormat.format(Date())

                                    val db = DBHelper4(ItemView.context, null)
                                    val cursor = db.getDetails()

                                    if(cursor!!.moveToFirst()){
                                        do{
                                            val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.NAME_COL))
                                            val datePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.DATE_COL))
                                            val idPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.ID_COL))
                                            val logOutCodePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper4.LOG_OUT_CODE))

                                            if(name == namePrint.toString() && date == datePrint.toString() && code == logOutCodePrint.toString()){
                                                val id = idPrint.toLong()

                                                val makeDateFormat2 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                                val timeOut = makeDateFormat2.format(Date())

                                                db.updateVisitor(id, timeOut)
                                                dialog.dismiss()

                                                val dialog1 = MaterialAlertDialogBuilder(ItemView.context,R.style.RoundedMaterialDialog).setView(R.layout.dialog_confirmattendance).show()

                                                val attendanceMessage = dialog1.findViewById<TextView>(R.id.attendanceMessage)
                                                val displayMessage = "LOG OUT SUCCESSFUL!"
                                                if (attendanceMessage != null) {
                                                    attendanceMessage.text = displayMessage
                                                }
                                                val txtAttendanceConfirm = dialog1.findViewById<TextView>(R.id.txtAttendanceConfirm)
                                                if (txtAttendanceConfirm != null) {
                                                    val displayName = "Name:\n$name"
                                                    txtAttendanceConfirm.text = displayName
                                                }
                                                val txtTimeAttendanceConfirm = dialog1.findViewById<TextView>(R.id.txtTimeAttendanceConfirm)
                                                if (txtTimeAttendanceConfirm != null) {
                                                    val displayTime = "Time out:\n$timeOut"
                                                    txtTimeAttendanceConfirm.text = displayTime
                                                }
                                                val btnOkAttendanceConfirm = dialog1.findViewById<Button>(R.id.btnOkAttendanceConfirm)
                                                if (btnOkAttendanceConfirm != null) {
                                                    btnOkAttendanceConfirm.setOnClickListener {
                                                        dialog1.dismiss()
                                                        dialog1.onBackPressed()
                                                    }
                                                }
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
    }
}