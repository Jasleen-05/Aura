package com.example.aura.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.example.aura.R


class HomeFragment : Fragment() {

    lateinit var btnAlertFamily: ImageButton
    lateinit var btnMedicalConnect: ImageButton
    lateinit var btnAskDoctor: ImageButton
    lateinit var btnShoutOut: ImageButton
    lateinit var btnNearby: ImageButton
    lateinit var btnBodyGuard: ImageButton
    lateinit var alertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        val emergencyButton: Button = view.findViewById(R.id.alertButton)
        emergencyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Emergency Alert Sent!", Toast.LENGTH_SHORT).show()
        }
        val alertFamilyButton: ImageButton = view.findViewById(R.id.btnAlertFamily)
        alertFamilyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Alerting Family...", Toast.LENGTH_SHORT).show()
        }
        val medicalConnectButton: ImageButton = view.findViewById(R.id.btnMedicalConnect)
        medicalConnectButton.setOnClickListener {
            Toast.makeText(requireContext(), "Connecting to Medical Help...", Toast.LENGTH_SHORT).show()
        }
        val askDoctorButton: ImageButton = view.findViewById(R.id.btnAskDoctor)
        askDoctorButton.setOnClickListener {
            Toast.makeText(requireContext(), "Sending location to Police", Toast.LENGTH_SHORT).show()
        }
        val shoutOutButton: ImageButton = view.findViewById(R.id.btnShoutOut)
        shoutOutButton.setOnClickListener {
            Toast.makeText(requireContext(), "ShoutOut sent! Help is on the way", Toast.LENGTH_SHORT).show()
        }
        val nearbyButton: ImageButton = view.findViewById(R.id.btnNearby)
        nearbyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Alerting Nearby Areas", Toast.LENGTH_SHORT).show()
        }
        val bodyGuardButton: ImageButton = view.findViewById(R.id.btnBodyGuard)
        bodyGuardButton.setOnClickListener {
            Toast.makeText(requireContext(), "Sent location to Emergency Contacts", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}