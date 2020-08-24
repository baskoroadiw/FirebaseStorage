package com.baskoroadi.firebasestorage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    object Constants {
        const val FILE_CHOOSE_CODE = 16
    }

    private lateinit var viewSnack : View
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewSnack = findViewById(R.id.main_constraint)
        progressBar.visibility = View.GONE

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        buttonBrowse.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(ACTION_GET_CONTENT)

            try {
                startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    Constants.FILE_CHOOSE_CODE
                )
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){
            Constants.FILE_CHOOSE_CODE ->
                if (resultCode == RESULT_OK){
                    val filePath = data?.data!!
                    uploadFile(filePath)
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uploadFile(fileUri : Uri){
        progressBar.visibility = View.VISIBLE

        Log.d("apaini",fileUri.path.toString())
        val file = Uri.fromFile(File(fileUri.path.toString()))
        val uploadRef = storageRef.child("images/${file.lastPathSegment}")
        val uploadTask = uploadRef.putFile(fileUri)

        uploadTask.addOnFailureListener {
            Snackbar.make(viewSnack,"Error",Snackbar.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            progressBar.visibility = View.GONE
            Snackbar.make(viewSnack,"Upload Success",Snackbar.LENGTH_SHORT).show()
        }
    }
}