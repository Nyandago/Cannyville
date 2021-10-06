package com.cannybits.cannyville

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {


    private lateinit var uploadPhoto: Button
    private lateinit var signUpEmail: EditText
    private lateinit var signUpPassword: EditText
    private lateinit var signUp : Button
    private lateinit var userPhoto: ImageView


    private val pickImage = 100
    private var imageUri : Uri? = null

    private var mAuth : FirebaseAuth? = null
    private var database= FirebaseDatabase.getInstance()
    private var myRef=database.reference



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
                if(task.isSuccessful) {
                    Toast.makeText(this, "User Successfully Signed Up", Toast.LENGTH_LONG).show()
                    saveImageToFirebase()
                } else {
                    Toast.makeText(this,"Failed To Sign Up The User", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveImageToFirebase(){
        val currentUser = mAuth!!.currentUser
        val email: String = currentUser!!.email.toString()
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs://canny-social.appspot.com")
        val dateFmt = SimpleDateFormat("ddMMyyHHmmss")
        val dateObj = Date()
        val imagePath = splitString(email) +"."+ dateFmt.format(dateObj) + ".jpg"
        val imageRef = storageRef.child("images/"+imagePath)
        userPhoto.isDrawingCacheEnabled = true
        userPhoto.buildDrawingCache()

        val drawable = userPhoto.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val byteOs = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteOs)
        val data = byteOs.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(this,"Failed To upload the image",Toast.LENGTH_LONG).show()
        }
                 .addOnSuccessListener { taskSnapshot ->
                     val downloadUrl= taskSnapshot.storage.downloadUrl.toString()!!

                     myRef.child("Users").child(currentUser.uid).child("email").setValue(currentUser.email)
                     myRef.child("Users").child(currentUser.uid).child("ProfileImage").setValue(downloadUrl)
                    loadTweets()
            }

    }

    private fun loadTweets(){
        val currentUser =mAuth!!.currentUser

        if(currentUser!=null) {


            val intent = Intent(this, TweetsActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)
        }
    }

    private fun splitString(email: String): String{
        val split = email.split("@")
        return split[0]
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