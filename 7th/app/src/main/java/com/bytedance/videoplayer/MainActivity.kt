package com.bytedance.videoplayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.net.Uri.*
import android.net.VpnService.prepare
import android.support.v7.app.AppCompatActivity
import android.os.PersistableBundle
import android.util.Log
import android.os.Bundle
import android.os.Environment
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.VideoView

class MainActivity:AppCompatActivity() {


    private var position = 0
    private var mediaController:MediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(mediaController == null){
            mediaController = MediaController(this@MainActivity)
        }

        try{
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.test_video))
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

}