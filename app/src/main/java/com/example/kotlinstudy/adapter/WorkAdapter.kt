package com.example.kotlinstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.info.WorkItemInfo
import com.example.kotlinstudy.utils.KotlinUtils

class WorkAdapter(val workItemList : List<WorkItemInfo>,var clickListenner: onItemClickListenner) : RecyclerView.Adapter<WorkAdapter.MyViewHolder>() {
    val tag:String="WorkAdapter"

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        var workIcon : ImageView = view.findViewById(R.id.work_icon)
        var workName : TextView = view.findViewById(R.id.work_name)
        var workData : TextView = view.findViewById(R.id.work_data)
        var itemlayout:LinearLayout= view.findViewById(R.id.work_item_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.work_item, parent, false)
        var myHolder=MyViewHolder(view)
        with(myHolder){
            itemlayout.setOnClickListener{
                KotlinUtils.log(tag,"item click is:"+myHolder.adapterPosition)
                if(clickListenner!=null){
                    clickListenner.onClick(myHolder.adapterPosition)
                }
            }
        }
        return  myHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var workInfo = workItemList[position]
        with(holder){
            workIcon.setImageDrawable(workInfo.iconDrawable)
            workName.text = workInfo.name
            workData.text = workInfo.data
        }
        //holder.appIcon.setImageDrawable(appInfo.iconDrawable)
        //holder.appName.text = appInfo.name
    }


    override fun getItemCount() = workItemList.size

    interface onItemClickListenner{
        fun onClick(pos:Int)
    }
}