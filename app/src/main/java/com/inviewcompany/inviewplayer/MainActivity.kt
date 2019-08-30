package com.inviewcompany.inviewplayer
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.Toast



class MainActivity : AppCompatActivity() {
    var Choosen_Directory:String=Environment.getExternalStorageDirectory().toString()
    val PERMISSION_REQUEST_CODE = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var button:Button= this.findViewById(R.id.choose_directory)
            button.setOnClickListener {
                if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
                } else {
                    // val intentChooseDirectory = Intent()
                    //        .setType("file/*")
                    //       .setAction(Intent.ACTION_GET_CONTENT)
                    //startActivityForResult(Intent.createChooser(intentChooseDirectory, "Select a folder"), 100)
                    val intentMusicPlayer = Intent(this,MusicList::class.java)
                    startActivity(intentMusicPlayer)

                }
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            var FilePath:String = data!!.getData().getPath()
            var FileName:String = data.getData().lastPathSegment
            var lastPos:Int=FilePath.length-FileName.length
            Choosen_Directory=FilePath.substring(0,lastPos)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                // val intentChooseDirectory = Intent()
                //        .setType("file/*")
                 //       .setAction(Intent.ACTION_GET_CONTENT)
                //startActivityForResult(Intent.createChooser(intentChooseDirectory, "Select a folder"), 100)
                val intentMusicList = Intent(this,MusicList::class.java)
                startActivity(intentMusicList)
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }

