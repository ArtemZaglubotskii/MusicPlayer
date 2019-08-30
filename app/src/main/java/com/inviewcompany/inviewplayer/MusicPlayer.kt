package com.inviewcompany.inviewplayer

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.inviewcompany.inviewplayer.adapters.SongListAdapter

class MusicPlayer : AppCompatActivity() {
    var mediaPlayer:MediaPlayer?=null
    var pause:Boolean=false

    companion object {
        val musicList="musiclist"
        val MusicItemPos="pos"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        var btnDropDown:ImageButton=this.findViewById(R.id.dropdown)
        var btnPlayOrPause:ImageButton=this.findViewById(R.id.btn_playorpause)
        var seekBar: SeekBar=this.findViewById(R.id.seek_bar)

        var songNameText:TextView=this.findViewById(R.id.songT)
        var songAuthorText:TextView=this.findViewById(R.id.songA)


        var pos=intent!!.getIntExtra(SongListAdapter.MusicItemPos,0)
        var AllMusicList=intent!!.getStringArrayListExtra(SongListAdapter.MusicList)
        var songNameList=intent!!.getStringArrayListExtra(SongListAdapter.songNameList)
        var songAuthorList=intent!!.getStringArrayListExtra(SongListAdapter.songAuthorList)

        songNameText.text= songNameList[pos].toString()
        songAuthorText.text= songAuthorList[pos].toString()


        if(mediaPlayer != null){
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }


        mediaPlayer= MediaPlayer()
        mediaPlayer!!.setDataSource(AllMusicList[pos])
        mediaPlayer!!.prepare()

        mediaPlayer!!.start()





        //Управление треком
        btnPlayOrPause.setOnClickListener{
            if(pause==false){
                mediaPlayer!!.pause()
                btnPlayOrPause.setImageResource(R.drawable.ic_play_48)
                pause=true
            } else {
                mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition)
                mediaPlayer!!.start()
                pause=false
                btnPlayOrPause.setImageResource(R.drawable.ic_pause_48)

            }


        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer!!.seekTo(progress*1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        //Кнопка закрытия плеера
        btnDropDown.setOnClickListener{
            val intentMusicList = Intent(this,MusicList::class.java)
            startActivity(intentMusicList)
        }


    }


}



