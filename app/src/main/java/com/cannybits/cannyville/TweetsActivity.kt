package com.cannybits.cannyville

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_tweets.*
import kotlinx.android.synthetic.main.add_tweet.view.*
import kotlinx.android.synthetic.main.tweets_view.view.*
import java.util.ArrayList

class TweetsActivity : AppCompatActivity() {

    private lateinit var logOut : Button

    private var tweetList: ArrayList<TweetTicket> = ArrayList()
    private var adapter:MyTweetAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)

        logOut = btnLogOut

        logOut.setOnClickListener {
            getInstance().signOut()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


        //Dummy Data
        tweetList.add(TweetTicket("tweetId", "tweetText", "tweetImageUrl", "personUID"))
        tweetList.add(TweetTicket("000", "hello there!", "tweetImageUrl", "canny"))

        adapter= MyTweetAdapter(this,tweetList)
        lvTweets.adapter=adapter
    }
    inner class  MyTweetAdapter: BaseAdapter {
        var tweetsAdapter=ArrayList<TweetTicket>()
        var context: Context?=null
        constructor(context:Context, tweetsAdapter:ArrayList<TweetTicket>):super(){
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


                var myView = layoutInflater.inflate(R.layout.add_tweet, null)

                myView.iv_attach.setOnClickListener(View.OnClickListener {
                  //  loadImage()

                })
                return myView
                        }
            }

        }




