package com.bytedance.videoplayer

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity:AppCompatActivity() {


    private var position = 0
    private var mediaController:MediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utils.verifyStoragePermissions(this)
        var uri: Uri? = if(Intent.ACTION_VIEW == intent.action)
            Uri.parse(intent.dataString)
        else
            intent.getParcelableExtra<Uri?>("VideoUri")

        if(uri == null)
        {
            Toast.makeText(this,"No uri specified!", Toast.LENGTH_LONG).show()
            return
        }


        if(mediaController == null){
            mediaController = MediaController(this@MainActivity)
        }

        position = savedInstanceState?.getInt("Position") ?: 0
        hideActionBar()
        try{
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(uri)
        }catch (e:Exception){
            Log.e("Error",e.message)
        }
        videoView.requestFocus()
        videoView.setOnPreparedListener{
            videoView.seekTo(position)
            if (position == 0){
                videoView.start()
            }else{
                videoView.pause()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(outState != null){
            outState.putInt("Position",videoView.currentPosition)
        }
        videoView.pause()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState != null){
            position = savedInstanceState.getInt("Position")
        }
        videoView.seekTo(position)
    }
    private fun hideActionBar(){
        val configuration=resources.configuration
        if(configuration.orientation== Configuration.ORIENTATION_LANDSCAPE){
            supportActionBar?.hide()
        }else {
            supportActionBar?.show()
        }
    }

}