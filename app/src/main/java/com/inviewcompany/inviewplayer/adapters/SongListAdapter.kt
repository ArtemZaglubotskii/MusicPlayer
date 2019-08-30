package com.inviewcompany.inviewplayer.adapters


import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import com.inviewcompany.inviewplayer.Interface.CustomItemClickListener
import com.inviewcompany.inviewplayer.MusicPlayer
import com.inviewcompany.inviewplayer.R
import com.inviewcompany.inviewplayer.model.SongModel
import com.inviewcompany.inviewplayer.service.PlayMusicService
import java.util.concurrent.TimeUnit


class SongListAdapter(SongModel:ArrayList<SongModel>, context:Context ):RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {
    var mContext=context
    var mSongModel = SongModel
    var AllMusicList:ArrayList<String> = ArrayList()
    var AllSongNameList:ArrayList<String> = ArrayList()
    var AllSongAuthorList:ArrayList<String> = ArrayList()
    companion object{
        val MusicList="musiclist"
        val songNameList="songnamelist"
        val songAuthorList="songauthprlist"
        val MusicItemPos="pos"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
      var view = LayoutInflater.from(parent!!.context).inflate(R.layout.music_row,parent,false)
        return SongListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mSongModel.size
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        var model=mSongModel[position]
        var songName=model.mSongName
        var songAuthor=model.mSongAuthor
        var songDuration=millitominandsec(model.mSongDuration.toLong())
        var imagePath=model.mImagePath
        holder.songTV.text=songName
        holder.durationTV.text=songDuration
        holder.songAuthor.text=songAuthor

        holder.setCustomItemClickListener(object:CustomItemClickListener{
            override fun onCustomItemClick(view: View, pos: Int) {

                for (i in 0 until mSongModel.size){
                    AllMusicList.add(mSongModel[i].mSongPath)
                    AllSongAuthorList.add(mSongModel[i].mSongAuthor)
                    AllSongNameList.add(mSongModel[i].mSongName)
                }
                Toast.makeText(mContext,"Song:"+songName+ MusicItemPos,Toast.LENGTH_SHORT).show()

                var musicPlayer=Intent(mContext,MusicPlayer::class.java)
                musicPlayer.putStringArrayListExtra(songNameList, AllSongNameList)
                musicPlayer.putStringArrayListExtra(songAuthorList, AllSongAuthorList)
                musicPlayer.putStringArrayListExtra(MusicList, AllMusicList)
                musicPlayer.putExtra(MusicItemPos,pos)
                mContext.startActivity(musicPlayer)

            }
        })

    }

    fun millitominandsec(millis:Long):String{
        var duration= String.format("%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(millis),
        TimeUnit.MILLISECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
        return duration
    }

    class SongListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var songTV:TextView
        var durationTV:TextView
        var albumArt:ImageView
        var songAuthor:TextView
        var mCustomItemClickListener:CustomItemClickListener?=null
        init {
            songTV=itemView.findViewById(R.id.song_name)
            songAuthor=itemView.findViewById(R.id.song_Author)
            durationTV=itemView.findViewById(R.id.song_duration)
            albumArt=itemView.findViewById(R.id.img_view)
            itemView.setOnClickListener(this)

        }
        fun setCustomItemClickListener(customItemClickListener:CustomItemClickListener){
            this.mCustomItemClickListener=customItemClickListener
        }

        override fun onClick(view: View) {
            this.mCustomItemClickListener!!.onCustomItemClick(view,adapterPosition)
        }
    }
}

