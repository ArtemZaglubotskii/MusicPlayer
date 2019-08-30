package com.inviewcompany.inviewplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.inviewcompany.inviewplayer.MusicPlayer
import com.inviewcompany.inviewplayer.adapters.SongListAdapter

class PlayMusicService:Service() {
    var currentPos:Int=0
    var musicDataList:ArrayList<String> = ArrayList()
    var MediaP:MediaPlayer?=null

    override fun onBind(intent: Intent?): IBinder ?{
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        currentPos=intent!!.getIntExtra(MusicPlayer.MusicItemPos, 0)
        musicDataList=intent.getStringArrayListExtra(MusicPlayer.musicList)


        if(MediaP != null){
                MediaP!!.stop()
                MediaP!!.release()
                MediaP = null
        }


        MediaP= MediaPlayer()
        MediaP!!.setDataSource(musicDataList[currentPos])
        MediaP!!.prepare()

        MediaP!!.setOnPreparedListener{
            MediaP!!.start()

        }
        return super.onStartCommand(intent, flags, startId)
    }
}