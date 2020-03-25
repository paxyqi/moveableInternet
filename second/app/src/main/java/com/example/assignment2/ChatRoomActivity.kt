package com.example.assignment2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView



class ChatRoomActivity : AppCompatActivity() {

    private lateinit var messageView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)
        messageView = findViewById(R.id.tv_content_info)
        val name = intent.extras?.getString("name")
        val index = intent.extras?.getInt("index")
        messageView.text = "Index of the message you clicked: ${index}"
        findViewById<TextView>(R.id.tv_with_name).text = name
        val sendButton: ImageView = findViewById(R.id.btn_send_info)
        sendButton.setOnClickListener { _ ->
            val msg = findViewById<EditText>(R.id.ed_say).text
            val contentView = findViewById<LinearLayout>(R.id.main_chat_content)
            val toAddMsg = TextView(this)
            toAddMsg.text = msg
            toAddMsg.setTextAppearance(R.style.InputedMessage)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            toAddMsg.layoutParams = params
            contentView.addView(toAddMsg, params)
            toAddMsg.setPadding(messageView.paddingLeft, messageView.paddingTop, messageView.paddingRight, messageView.paddingBottom)
            toAddMsg.textSize = 14F
            toAddMsg.gravity = Gravity.RIGHT
            msg.clear()
        }
    }
}
