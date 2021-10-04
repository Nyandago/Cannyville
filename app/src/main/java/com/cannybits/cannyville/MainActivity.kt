package com.cannybits.cannyville

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var uploadPhoto: Button
    private lateinit var signUpEmail: EditText
    private lateinit var signUpPassword: EditText
    private lateinit var signUp : Button
    private lateinit var userPhoto: ImageView


    private val pickImage = 100
    private var imageUri : Uri? = null

    private var mAuth : FirebaseAuth? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        initView()
        mAuth = FirebaseAuth.getInstance()

        uploadPhoto.setOnClickListener {
            loadImage()
        }

        signUp.setOnClickListener {
            val userEmail = signUpEmail.text.toString()
            val userPassword = signUpPassword.text.toString()

            signUpUserToFirebase(userEmail, userPassword)
        }
    }

    private fun signUpUserToFirebase(email: String, password: String){
        mAuth!!.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener() {
                    task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"User Successfully Signed Up", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"Failed To Sign Up The User", Toast.LENGTH_LONG).show()
                }
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