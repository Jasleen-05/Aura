package com.example.aura.fragment

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aura.R
import com.example.aura.adapter.ChatAdapter

class LegalBotFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var chatAdapter: ChatAdapter
    private val messageList = mutableListOf<com.example.aura.model.Message>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_legal_bot, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewChat)
        messageEditText = view.findViewById(R.id.editTextMessage)
        sendButton = view.findViewById(R.id.buttonSend)

        chatAdapter = ChatAdapter(messageList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = chatAdapter

        sendButton.setOnClickListener {
            val userMessage = messageEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                messageList.add(com.example.aura.model.Message(userMessage, isUser = true))
                chatAdapter.notifyItemInserted(messageList.size - 1)
                messageEditText.text.clear()
                getLegalResponse(userMessage)
            }
        }
    }
    private fun getLegalResponse(query: String) {
        val response = when {
            query.contains("FIR", ignoreCase = true) ->
                "To file an FIR, you can visit your local police station or use state-level e-FIR portals where available."

            query.contains("harassment", ignoreCase = true) ->
                "Sexual harassment is covered under IPC Section 354A and the POSH Act, 2013."

            query.contains("cybercrime", ignoreCase = true) ->
                "Cybercrime issues can be reported at https://cybercrime.gov.in or at your nearest cyber cell."

            else -> "I’m here to help! You can ask about filing FIRs, domestic violence laws, workplace harassment, etc."
        }

        messageList.add(com.example.aura.model.Message(response, isUser = false))
        chatAdapter.notifyItemInserted(messageList.size - 1)
        recyclerView.scrollToPosition(messageList.size - 1)
    }
}