package com.example.kotlinstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.info.AppInfo
import com.example.kotlinstudy.utils.KotlinUtils

class MyAdapter(val applist : List<AppInfo>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    val tag:String="MyAdapter"

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val appIcon : ImageView = view.findViewById(R.id.appicon)
        val appName : TextView = view.findViewById(R.id.appname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_item, parent, false)
        var myHolder=MyViewHolder(view)
        with(myHolder){
            appName.setOnClickListener{
                KotlinUtils.log(tag,"item click is:"+myHolder.adapterPosition)
            }
        }
        return  myHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val appInfo = applist[position]
        //with(holder){
        holder.appIcon.setImageDrawable(appInfo.iconDrawable)
        holder.appName.text = appInfo.name
        //}
        //holder.appIcon.setImageDrawable(appInfo.iconDrawable)
        //holder.appName.text = appInfo.name
    }

    override fun getItemCount() = applist.size
}

