package com.ruthertech.foodgenics

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_item_not_found.view.*
import java.io.ByteArrayOutputStream


class ItemNotFound(okunanBarkod: String) : DialogFragment() {

    var okunanBarkod = okunanBarkod
     var mContext : FragmentActivity? = null
    var izinlerVerildi = false
    lateinit var send_photo_btn : Button
    var imagePathFromCamera: Bitmap? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_item_not_found, container, false)

        mContext = activity
        send_photo_btn = view.sendphoto

        send_photo_btn.setOnClickListener {
            if (izinlerVerildi){

                if (imagePathFromCamera != null){
                    var stream = ByteArrayOutputStream()
                    imagePathFromCamera?.compress(Bitmap.CompressFormat.JPEG,90,stream)

                    uploadPhotoToFirebase(stream.toByteArray())
                }
                else{
                    var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent,99)
                    send_photo_btn.text = "TAKE PHOTO"
                }

            }
            else{
                checkPermission()
            }


        }



        return view
    }









    private fun uploadPhotoToFirebase(result: ByteArray?) {

        Toast.makeText(mContext,"baslangic",Toast.LENGTH_LONG).show()

        var storageRef = FirebaseStorage.getInstance().getReference()
        var fref = FirebaseDatabase.getInstance().reference

        val ref = storageRef.child("images/fromusers").child(okunanBarkod).child("urun_resmi")

        var uploadTask = ref.putBytes(result!!)

        Toast.makeText(mContext,"Image added to database. Thanks :D",Toast.LENGTH_LONG).show()

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl

        }

        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var photoURL:String = ""
                ref.downloadUrl.addOnSuccessListener {uri ->




                    photoURL = uri.toString()
                    var obje = GELENURUNDATA()
                    obje.urun_barkod = okunanBarkod
                    obje.urun_resmi = photoURL

                    FirebaseDatabase.getInstance().reference
                        .child("itemsfromusers")
                        .child(okunanBarkod)
                        .setValue(obje)
                }
            } else {
                Toast.makeText(mContext,"${task.exception!!.message}",Toast.LENGTH_LONG).show()

            }
        }

        dialog?.dismiss()

    }


    private fun checkPermission() {
        var izinler = arrayOf(android.Manifest.permission.CAMERA)
        if (ContextCompat.checkSelfPermission(context!!,izinler[0]) == PackageManager.PERMISSION_GRANTED)
        {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,99)

                izinlerVerildi = true
        }
        else{
            ActivityCompat.requestPermissions(activity!!,izinler,150)
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 150){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    izinlerVerildi
                    var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent,99)
                }

            }
            else{
                Toast.makeText(context,"Tum izinleri kabul edin", Toast.LENGTH_LONG).show()
                !izinlerVerildi
            }


        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //from camera
         if (requestCode == 99 && resultCode == Activity.RESULT_OK && data !=null&& izinlerVerildi){

             imagePathFromCamera = data.extras?.get("data") as Bitmap
            Toast.makeText(mContext!!, "Resim Eklendi!",Toast.LENGTH_LONG).show();
             send_photo_btn.text = "GÖNDER"



         }


    }




    }

