package com.cannybits.cannyville

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var logOut: Button

    private var tweetList: ArrayList<TweetTicket> = ArrayList()
    private var adapter:MyTweetAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logOut = btnLogOut

        logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        //Dummy Data
        tweetList.add(TweetTicket("tweetId", "tweetText", "tweetImageUrl", "personUID"))
        tweetList.add(TweetTicket("000", "hello there!", "tweetImageUrl", "canny"))
        tweetList.add(TweetTicket("123", "Hakuna Matata!", "myImageUrl", "add"))

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
                layoutInflater.inflate(R.layout.add_tweet, null)
            } else{
                //Load Tweet View
                layoutInflater.inflate(R.layout.tweets_view, null)
            }
        }
    }

}


