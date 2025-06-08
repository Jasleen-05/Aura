package com.example.aura.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aura.R
import com.example.aura.model.Message

class ChatAdapter(private val messages: List<com.example.aura.model.Message>) :
RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userMessageLayout: View = itemView.findViewById(R.id.userMessageLayout)
        val botMessageLayout: View = itemView.findViewById(R.id.botMessageLayout)
        val userMessageTextView: TextView = itemView.findViewById(R.id.userMessageTextView)
        val botMessageTextView: TextView = itemView.findViewById(R.id.botMessageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        if (message.isUser) {
            holder.userMessageLayout.visibility = View.VISIBLE
            holder.botMessageLayout.visibility = View.GONE
            holder.userMessageTextView.text = message.text
        } else {
            holder.botMessageLayout.visibility = View.VISIBLE
            holder.userMessageLayout.visibility = View.GONE
            holder.botMessageTextView.text = message.text
        }
    }

    override fun getItemCount(): Int = messages.size
}