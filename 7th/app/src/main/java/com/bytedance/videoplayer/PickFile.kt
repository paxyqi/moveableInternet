package com.bytedance.videoplayer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.bytedance.videoplayer.Utils.verifyStoragePermissions

class PickFile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_file)
        findViewById<Button>(R.id.pick_file_button).setOnClickListener {
            Utils.verifyStoragePermissions(this)
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "video/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("VideoUri",uri)
            startActivity(intent)
        }
    }
}