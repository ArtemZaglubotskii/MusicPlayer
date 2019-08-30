package com.inviewcompany.inviewplayer

import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import com.inviewcompany.inviewplayer.adapters.SongListAdapter
import com.inviewcompany.inviewplayer.model.SongModel
import kotlinx.android.synthetic.main.activity_music_list.*

class MusicList : AppCompatActivity() {
    var mFileList:ArrayList<String> = ArrayList()
    var songModelData: ArrayList<SongModel> = ArrayList()
    var songListAdapter: SongListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_list)
        loadData()
    }

//Загружает всю необходимую информацию
    fun loadData() {
        var songCursor: Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null, null, null, null)
        while (songCursor != null && songCursor.moveToNext()) {
            var songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            var songDuration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            var songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            var songAuthor=songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            var ImagePath=""
            songModelData.add(SongModel(songName, songDuration,songPath,ImagePath,songAuthor))
        }
        songListAdapter = SongListAdapter(songModelData, applicationContext)
        var layoutManager = LinearLayoutManager(applicationContext)

        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = songListAdapter
    }
}
