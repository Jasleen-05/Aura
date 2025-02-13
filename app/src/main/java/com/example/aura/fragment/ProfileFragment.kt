package com.example.aura.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura.R
import com.example.aura.adapter.DashboardRecyclerAdapter
import com.example.aura.model.Profile

class ProfileFragment : Fragment() {

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    val profileList= arrayListOf<Profile>(
        Profile("Name: Arhasi Soni",R.drawable.women,R.color.name),
        Profile("Mobile No: 9827123456",R.drawable.phone,R.color.mobile),
        Profile("Email Id: arhasisoni@gmail.com",R.drawable.email,R.color.email)
    )
    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)
        layoutManager= LinearLayoutManager(activity)
        recyclerAdapter=DashboardRecyclerAdapter(activity as Context, profileList)
        recyclerDashboard.adapter=recyclerAdapter
        recyclerDashboard.layoutManager=layoutManager
        recyclerDashboard.addItemDecoration(DividerItemDecoration(recyclerDashboard.context,(layoutManager as LinearLayoutManager).orientation))
        return view
    }
}