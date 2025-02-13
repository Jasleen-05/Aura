package com.example.aura.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.aura.R

class SafetyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_safety, container, false)
        val btnLiveSafety = view.findViewById<Button>(R.id.btnLiveSafety)
        val btnSafeZone = view.findViewById<ImageButton>(R.id.btnSafeZone)
        val btnSafeSOS = view.findViewById<ImageButton>(R.id.btnSafeSOS)
        val btnInstantSOS = view.findViewById<ImageButton>(R.id.btnInstantSOS)

        btnLiveSafety.setOnClickListener {
            Toast.makeText(requireContext(), "Live Safety Activated", Toast.LENGTH_SHORT).show()
        }

        btnSafeZone.setOnClickListener {
            Toast.makeText(requireContext(), "Companion Mode Activated", Toast.LENGTH_SHORT).show()
        }

        btnSafeSOS.setOnClickListener {
            //Toast.makeText(requireContext(), "Safe SOS Activated", Toast.LENGTH_SHORT).show()
        }

        btnInstantSOS.setOnClickListener {
            //Toast.makeText(requireContext(), "Instant SOS Sent!", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}