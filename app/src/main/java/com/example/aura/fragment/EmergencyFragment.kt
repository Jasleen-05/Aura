package com.example.aura.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import com.example.aura.R

class EmergencyFragment : Fragment() {

    lateinit var imageView: ImageView
    lateinit var btnEmergency: Button
    lateinit var btnAlertFamily: Button
    lateinit var btnMedicalConnect: Button
    lateinit var btnAskDoctor: Button
    lateinit var btnShoutOut: Button
    lateinit var btnNearby: Button
    lateinit var btnBodyGuard: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_emergency, container, false)
        imageView=view.findViewById(R.id.imageView)
        val emergencyButton: Button = view.findViewById(R.id.btnEmergency)
        emergencyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Emergency Alert Sent!", Toast.LENGTH_SHORT).show()
        }
        val alertFamilyButton: Button = view.findViewById(R.id.btnAlertFamily)
        alertFamilyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Alerting Family...", Toast.LENGTH_SHORT).show()
        }
        val medicalConnectButton: Button = view.findViewById(R.id.btnMedicalConnect)
        medicalConnectButton.setOnClickListener {
            Toast.makeText(requireContext(), "Connecting to Medical Help...", Toast.LENGTH_SHORT).show()
        }
        val askDoctorButton: Button = view.findViewById(R.id.btnAskDoctor)
        askDoctorButton.setOnClickListener {
            Toast.makeText(requireContext(), "Sending Doctor Contact Details", Toast.LENGTH_SHORT).show()
        }
        val shoutOutButton: Button = view.findViewById(R.id.btnShoutOut)
       shoutOutButton.setOnClickListener {
            Toast.makeText(requireContext(), "ShoutOut sent! Help is on the way", Toast.LENGTH_SHORT).show()
       }
        val nearbyButton: Button = view.findViewById(R.id.btnNearby)
        nearbyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Alerting Nearby Areas", Toast.LENGTH_SHORT).show()
        }
        val bodyGuardButton: Button = view.findViewById(R.id.btnBodyGuard)
        bodyGuardButton.setOnClickListener {
            Toast.makeText(requireContext(), "Security Assistance is on the way!", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}
