package com.kronos.timecardapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

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
        holder.cardProfile.tag = position

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

                when(val position = ItemView.tag){
                    position -> {
                        val dialog = BottomSheetDialog(ItemView.context)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.bottomsheet_profileaccess)

                        val nameVerify = txtViewNameProfile.text.toString()

                        val txtViewDisplayMessageProfileAccess = dialog.findViewById<TextView>(R.id.txtViewDisplayMessageProfileAccess)
                        val displayMessage = nameVerify

                        val btnCancelPINProfileAccess = dialog.findViewById<ImageView>(R.id.btnCancelPINProfileAccess)
                        if (btnCancelPINProfileAccess != null) {
                            btnCancelPINProfileAccess.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                        if (txtViewDisplayMessageProfileAccess != null) {
                            txtViewDisplayMessageProfileAccess.text = displayMessage
                        }

                        val editTxtEnterPINProfileAccess = dialog.findViewById<EditText>(R.id.editTxtEnterPINProfileAccess)

                        val btnEnterPINProfileAccess = dialog.findViewById<Button>(R.id.btnEnterPINProfileAccess)
                        if (btnEnterPINProfileAccess != null) {
                            btnEnterPINProfileAccess.setOnClickListener {
                                if (editTxtEnterPINProfileAccess != null) {
                                    if(editTxtEnterPINProfileAccess.text.toString().trim().isEmpty()){
                                        Toast.makeText(ItemView.context,"PROFILE PIN REQUIRED", Toast.LENGTH_SHORT).show()
                                    } else{
                                        val db = DBHelper(ItemView.context, null)
                                        val cursor = db.getLoginDetails()

                                        if(cursor!!.moveToFirst()){
                                            do{
                                                val namePrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
                                                val pinPrint = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PIN_NUMBER))

                                                if(nameVerify == namePrint.toString() && editTxtEnterPINProfileAccess.text.toString().trim() == pinPrint.toString()){
                                                    val intent = Intent(ItemView.context,ProfileViewActivity::class.java)

                                                    intent.putExtra("NamePass",nameVerify)
                                                    editTxtEnterPINProfileAccess.text.clear()
                                                    dialog.dismiss()
                                                    ItemView.context.startActivity(intent)

                                                }
                                            } while(cursor.moveToNext())
                                        }
                                        cursor.close()
                                    }
                                }
                            }
                        }
                        dialog.show()
                        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                        dialog.window!!.setGravity(Gravity.BOTTOM)
                    }
                }
            }
        }
    }
}
