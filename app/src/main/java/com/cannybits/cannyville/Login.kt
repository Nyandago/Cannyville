package com.cannybits.cannyville

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var uploadPhoto: Button
    private lateinit var signUpEmail: EditText
    private lateinit var signUpPassword: EditText
    private lateinit var signUp : Button
    private lateinit var userPhoto: ImageView


    private val pickImage = 100
    private var imageUri : Uri? = null

    private var mAuth : FirebaseAuth? = null
    private var mDatabase = FirebaseDatabase.getInstance()
    private var mRef = mDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        mAuth = FirebaseAuth.getInstance()

        signUp.setOnClickListener {
            val userEmail = signUpEmail.text.toString()
            val userPassword = signUpPassword.text.toString()

            signUpUserToFirebase(userEmail, userPassword)
        }





        userPhoto.setOnClickListener(
         View.OnClickListener {
             loadImage()
        })

        uploadPhoto.setOnClickListener(
            View.OnClickListener {
                loadImage()
            }
        )
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

    fun loginFunction(view: View){

    }

    private fun initView(){
        uploadPhoto = btnUserImage
        signUp = btnSignUp
        signUpEmail = etEmailSignup
        signUpPassword = etPasswordSignup
        userPhoto = imgUserPhoto
    }
}