package com.example.firebaseseoulopendata

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LessonListAdapter(val dataList:MutableList<SwimLessonDAO>) : RecyclerView.Adapter<LessonListAdapter.AdapterViewHolder>() {
    lateinit var context: Context
    inner class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCenter = itemView.findViewById<TextView>(R.id.tv_centerName)
        val tvWeek = itemView.findViewById<TextView>(R.id.tv_lessonWeek)
        val tvTime = itemView.findViewById<TextView>(R.id.tv_lessonTime)
        val tvFee = itemView.findViewById<TextView>(R.id.tv_lessonFee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview,parent,false)
        return  AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val data = dataList.get(position)
        holder.apply {
            tvCenter.text = data.center
            tvWeek.text = data.week
            tvTime.text = data.time
            tvFee.text = data.fee
        }
       holder.itemView.setOnClickListener{
           val intent = Intent(context, UpdateActivity::class.java)
           intent.putExtra("id", data.id)
           intent.putExtra("center", data.center)
           intent.putExtra("week", data.week)
           intent.putExtra("time", data.time)
           intent.putExtra("fee", data.fee)
           context.startActivity(intent)
           (context as Activity).finish()
       }
    }

    override fun getItemCount(): Int {
       return dataList.size
    }
}