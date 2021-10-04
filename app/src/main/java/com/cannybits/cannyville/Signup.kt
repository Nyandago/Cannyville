package com.cannybits.cannyville

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    private lateinit var uploadPhoto: Button
    private lateinit var signUpEmail: EditText
    private lateinit var signUpPassword: EditText
    private lateinit var signUp : Button
    private lateinit var userPhoto: ImageView


    private val pickImage = 100
    private var imageUri : Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)



        initView()

        uploadPhoto.setOnClickListener {

            loadImage()

        }
    }



    private fun loadImage(){
       val gallery  = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK && requestCode == pickImage)
        {
            imageUri = data?.data
            userPhoto.setImageURI(imageUri)
        }
    }

    private fun initView(){
        uploadPhoto = btnUserImage
        signUp = btnSignUp
        signUpEmail = etEmailSignup
        signUpPassword = etPasswordSignup
        userPhoto = imgUserPhoto
    }
}