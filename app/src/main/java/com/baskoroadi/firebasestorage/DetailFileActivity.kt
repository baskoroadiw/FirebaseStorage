package com.baskoroadi.firebasestorage

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_detail_file.*

class DetailFileActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_file)

        val filename = intent.getStringExtra("extraNameFile")

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        tv_detail_filename.visibility = View.GONE

        storageRef.child("images/${filename}").downloadUrl.addOnSuccessListener { data ->
            Glide.with(this)
                .load(data)
                .addListener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressbar_glide.visibility = View.GONE
                        tv_detail_filename.visibility = View.VISIBLE
                        tv_detail_filename.text = filename
                        return false
                    }
                })
                .into(imageview_file)
        }.addOnFailureListener {
            // Handle any errors
            Toast.makeText(this, "Error load gambar", Toast.LENGTH_SHORT).show()
        }

        imageview_file.setOnClickListener {
            val intent = Intent(this,DetailImageActivity::class.java).apply {
                putExtra("extraNameFile",filename)
            }
            startActivity(intent)
        }
    }
}