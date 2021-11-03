package com.cannybits.cannyville

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tweets.*
import kotlinx.android.synthetic.main.add_tweet.view.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var tweetList: ArrayList<TweetTicket> = ArrayList()
    private var adapter:MyTweetAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Dummy Data
        tweetList.add(TweetTicket("tweetId", "tweetText", "tweetImageUrl", "personUID"))
        tweetList.add(TweetTicket("000", "hello there!", "tweetImageUrl", "canny"))
        tweetList.add(TweetTicket("123", "Hakuna Matata!", "myImageUrl", "add"))

        adapter = MyTweetAdapter(this,tweetList)
        lvTweets.adapter=adapter
    }

    inner class  MyTweetAdapter: BaseAdapter {

        var tweetsAdapter=ArrayList<TweetTicket>()
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

            var mytweet=tweetsAdapter[p0]

            if(mytweet.tweetPersonUID.equals("add")){
                //Load add tweet ticket
            }



            var myView = layoutInflater.inflate(R.layout.add_tweet, null)

            myView.iv_attach.setOnClickListener(View.OnClickListener {
                //  loadImage()

            })
            return myView
        }
    }

}


