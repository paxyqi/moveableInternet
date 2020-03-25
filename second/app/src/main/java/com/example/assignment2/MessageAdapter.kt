package com.example.assignment2

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.assignment2.model.Message


class MessageAdapter(private val mMsgList: List<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleView: TextView = view.findViewById(R.id.tv_title)
        var descriptionView: TextView = view.findViewById(R.id.tv_description)
        var timeView: TextView = view.findViewById(R.id.tv_time)
        var officalBadge: ImageView = view.findViewById(R.id.robot_notice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.im_list_item, parent, false)
        val holder = ViewHolder(itemView)
        holder.itemView.setOnClickListener { v: View ->
            val intent = Intent(v.context, ChatRoomActivity::class.java)
            intent.putExtra("index", holder.adapterPosition)
            intent.putExtra("name",mMsgList[holder.adapterPosition].title)
            v.context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount(): Int = mMsgList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMsg = mMsgList[position]
        holder.titleView.text = currentMsg.title
        holder.descriptionView.text = currentMsg.description
        holder.timeView.text = currentMsg.time
        holder.officalBadge.visibility = if (currentMsg.isOfficial) View.VISIBLE else View.INVISIBLE
    }

}