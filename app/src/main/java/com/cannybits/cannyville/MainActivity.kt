package com.cannybits.cannyville

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_tweet.*
import kotlinx.android.synthetic.main.add_tweet.view.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var logOut: Button

    private val pickImage = 100
    private var imageUri : Uri? = null

    private var tweetList: ArrayList<TweetTicket> = ArrayList()
    private var adapter:MyTweetAdapter?=null

    private var myEmail : String? = null
    private var userUID : String? = null

    private var mDatabase = FirebaseDatabase.getInstance()
    private var mRef = mDatabase.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logOut = btnLogOut

        logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val b: Bundle = intent.extras!!
        myEmail = b.getString("email")
        userUID = b.getString("uid")


        //Dummy Data
        tweetList.add(TweetTicket("tweetId", "tweetText", "tweetImageUrl", "add"))
        tweetList.add(TweetTicket("000", "hello there!", "tweetImageUrl", "nyati"))
        tweetList.add(TweetTicket("123", "Hakuna Matata!", "myImageUrl", "kenge"))

        adapter = MyTweetAdapter(this,tweetList)
        lvTweets.adapter=adapter
    }

    inner class  MyTweetAdapter: BaseAdapter {



        private var tweetsAdapter=ArrayList<TweetTicket>()
        var context: Context?=null

        constructor(context: Context, tweetsAdapter:ArrayList<TweetTicket>):super(){
            this.tweetsAdapter=tweetsAdapter
            this.context=context
        }
        override fun getItem(p0: Int): Any {
            return tweetsAdapter[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {

            return tweetsAdapter.size

        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val myTweet = tweetsAdapter[p0]

            return if(myTweet.tweetPersonUID.equals("add")) {
                //Load add tweet ticket
                val myView = layoutInflater.inflate(R.layout.add_tweet, null)

                myView.iv_attach.setOnClickListener(
                    View.OnClickListener {
                        loadImage() })

                myView.iv_post.setOnClickListener(
                    View.OnClickListener {
                       mRef.child("posts").push().setValue(
                           PostInfo(myView.etPost.text.toString(),
                           userUID!!,
                           downloadUrl!!))

                myView.etPost.setText("")
                    })

                myView

            } else {
                //Load Tweet View
                layoutInflater.inflate(R.layout.tweets_view, null)
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
            uploadImage(BitmapFactory.decodeFile(imageUri!!.path))
        }
    }

    private var downloadUrl : String? = null

    private fun uploadImage(bitmap: Bitmap){
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs://canny-social.appspot.com")
        val dateFmt = SimpleDateFormat("ddMMyyHHmmss")
        val dateObj = Date()
        val imagePath = splitString(myEmail!!) +"_"+ dateFmt.format(dateObj) + ".jpg"
        val imageRef = storageRef.child("imagePost/"+imagePath)

        val byteOs = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteOs)
        val data = byteOs.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(this,"Failed to upload the image",Toast.LENGTH_LONG).show()
        }
            .addOnSuccessListener { taskSnapshot ->
                 downloadUrl= taskSnapshot.storage.downloadUrl.toString()
                Toast.makeText(this, "Image Uploaded: $downloadUrl",Toast.LENGTH_LONG).show()
            }
    }

    private fun splitString(email: String): String{
        val split = email.split("@")
        return split[0]
    }

}


