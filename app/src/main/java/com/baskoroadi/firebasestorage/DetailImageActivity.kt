package com.baskoroadi.firebasestorage

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.igreenwood.loupe.Loupe
import kotlinx.android.synthetic.main.activity_detail_image.*

class DetailImageActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_image)

        val filename = intent.getStringExtra("extraNameFile")

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        storageRef.child("images/${filename}").downloadUrl.addOnSuccessListener { data ->
            Glide.with(this)
                .load(data)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Loupe.create(image_view_fullscreen, container_image_fullscreen) { // imageView is your ImageView
                            onViewTranslateListener = object : Loupe.OnViewTranslateListener {

                                override fun onStart(view: ImageView) {
                                    // called when the view starts moving
                                }

                                override fun onViewTranslate(view: ImageView, amount: Float) {
                                    // called whenever the view position changed
                                }

                                override fun onRestore(view: ImageView) {
                                    // called when the view drag gesture ended
                                }

                                override fun onDismiss(view: ImageView) {
                                    // called when the view drag gesture ended
                                    finish()
                                }
                            }
                        }
                        return false
                    }
                })
                .into(image_view_fullscreen)
        }.addOnFailureListener {
            // Handle any errors
            Toast.makeText(this, "Error load gambar", Toast.LENGTH_SHORT).show()
        }
    }
}