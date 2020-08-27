package com.baskoroadi.firebasestorage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_upload.*


class UploadFragment : Fragment() {

    object Constants {
        const val FILE_CHOOSE_CODE = 16
    }

    private lateinit var viewSnack : View
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewSnack = view.findViewById(R.id.constraint_upload)
        progressBar.visibility = View.GONE

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        buttonBrowse.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            try {
                startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    Constants.FILE_CHOOSE_CODE
                )
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(context, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){
            Constants.FILE_CHOOSE_CODE ->
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    val filePath = data?.data!!
                    uploadFile(filePath)
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun uploadFile(fileUri: Uri){
        progressBar.visibility = View.VISIBLE

        val namefile = getFileName(fileUri).toString()
        val uploadRef = storageRef.child("images/${namefile}")
        val uploadTask = uploadRef.putFile(fileUri)

        uploadTask.addOnFailureListener {
            Snackbar.make(viewSnack,"Error", Snackbar.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            progressBar.visibility = View.GONE
            Snackbar.make(viewSnack,"Upload Success", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = activity?.contentResolver?.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }
}