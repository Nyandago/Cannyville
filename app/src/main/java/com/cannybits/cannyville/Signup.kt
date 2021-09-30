package com.cannybits.cannyville

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private val UPLOADIMAGE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initView()

        uploadPhoto.setOnClickListener {
            checkPermission()

        }
    }

    private fun checkPermission(){

        if (ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),UPLOADIMAGE)
            return
        }

        loadImage()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            UPLOADIMAGE -> {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    loadImage()
                } else{
                    Toast.makeText(this,"Photo Storage Permissions Denied",Toast.LENGTH_LONG).show()
                }
            }

            else ->  super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }


    }

    private fun loadImage(){
        //TODO: load image from phone
    }

    private fun initView(){
        uploadPhoto = btnUserImage
        signUp = btnSignUp
        signUpEmail = etEmailSignup
        signUpPassword = etPasswordSignup
        userPhoto = imgUserPhoto
    }
}