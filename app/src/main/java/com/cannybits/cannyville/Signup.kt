package com.cannybits.cannyville

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
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
    val UPLOADIMAGE = 123
    val PICK_IMAGE_CODE = 255


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
                    Toast.makeText(this,"Storage Permissions Denied",Toast.LENGTH_LONG).show()
                }
            }

            else ->  super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }


    }

    private fun loadImage(){
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==PICK_IMAGE_CODE && resultCode== RESULT_OK && data != null){
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = selectedImage?.let { contentResolver.query(it,filePathColumn,null,null,null) }
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val picturePath = columnIndex?.let { cursor?.getString(it) }
            cursor?.close()
            userPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initView(){
        uploadPhoto = btnUserImage
        signUp = btnSignUp
        signUpEmail = etEmailSignup
        signUpPassword = etPasswordSignup
        userPhoto = imgUserPhoto
    }
}