package com.example.aura.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.aura.R
import com.example.aura.model.Profile

class DashboardRecyclerAdapter(val context: Context,val itemList:ArrayList<Profile>):RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtName: TextView =view.findViewById(R.id.txtName)
        val imgpic:ImageView=view.findViewById(R.id.imgpic)
        val cardView: CardView = itemView.findViewById(R.id.serviceCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val profile=itemList[position]
        holder.txtName.text=profile.name
        holder.imgpic.setImageResource(profile.profileImage)
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, profile.backgroundColor))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}