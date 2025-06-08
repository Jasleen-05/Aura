package com.example.aura.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.aura.R

class LegalFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_legal, container, false)
        val imageButton = view.findViewById<ImageButton>(R.id.btnChatbot)
        imageButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, LegalBotFragment())
                .addToBackStack(null)
                .commit()

        }
        return view

    }
}