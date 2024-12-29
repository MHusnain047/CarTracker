package com.husnain.cartracker
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.husnain.cartracker.databinding.ItemCarBinding
class CarAdapter( val carList: List<CarDetails>) : RecyclerView.Adapter<CarViewHolder>() {

override fun getItemCount(): Int {
    return carList.size // Return the size of the sizes list
}


override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
    Log.i("SizeAdapter", "onCreateViewHolder")
    return CarViewHolder(
        ItemCarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}



   override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
    Log.i("SizeAdapter", "onBindViewHolder")
    val item = carList[position]
       holder.binding.tvCarModel.text = "${item.carmodel}"
       holder.binding.tvReg.text = "Reg. #: ${item.reg_number}"
       holder.binding.tvCarColor.text = "${item.carcolor}"
       holder.binding.tvDate.text = item.date
       holder.binding.tvAddress.text = item.address
       holder.binding.tvStatus.text = item.status


       holder.itemView.setOnClickListener {
           val intent = Intent(holder.itemView.context, CarDetailActivity::class.java).apply {
               putExtra("data", Gson().toJson(item)) // Serialized Lost object
               putExtra("nameinput", item.owner_name)
               putExtra("contactinput", item.owner_number)
               putExtra("date", item.date)
               putExtra("carmodel", item.carmodel)
               putExtra("carcolor", item.carcolor)
               putExtra("reg", item.reg_number)
               putExtra("addressinput", item.address)
               putExtra("status", item.status)
//               putExtra("email", item.e_mail)
               putExtra("isLost", item.isLost)
               putExtra("isFound", item.isFound)

           }

           holder.itemView.context.startActivity(intent)


               }

           }


       }


