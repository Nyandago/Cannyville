package com.cannybits.cannyville

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tweets_view.view.*
import java.util.ArrayList

class TweetsActivity : AppCompatActivity() {

    private var tweetList: ArrayList<TweetTicket> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets)


        //Dummy Data
        tweetList.add(TweetTicket("tweetId", "tweetText", "tweetImageUrl", "personUID"))
        tweetList.add(TweetTicket("000", "hello there!", "tweetImageUrl", "canny"))
    }

    inner class StudentAdapter : RecyclerView.Adapter<StudentAdapter.TweetViewHolder>() {

        private var twtList: ArrayList<TweetTicket> = ArrayList()
        private var onClickItem: ((TweetTicket) -> Unit)? = null
        private var onClickDeleteItem: ((TweetTicket) -> Unit)? = null

        fun addItems(items: ArrayList<TweetTicket>) {
            this.twtList = items
            notifyDataSetChanged()
        }

        fun setOnClickItem(callback: (TweetTicket) -> Unit) {
            this.onClickItem = callback
        }

        fun setOnClickDeleteItem(callback: (TweetTicket) -> Unit) {
            this.onClickDeleteItem = callback
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TweetViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tweets_view, parent, false)
        )

        override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
            val tweet = twtList[position]
            holder.bindView(tweet)
            holder.itemView.setOnClickListener { onClickItem?.invoke(tweet) }
          //  holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(tweet) }
        }

        override fun getItemCount(): Int {
            return twtList.size
        }

        inner class TweetViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
            private var userName = view.txtUserName
            private var picturePath = view.iv_picture_path
            private var tweetDate = view.txt_tweet_date
            private var tweet = view.txt_tweet
            //val btnDelete


            fun bindView(tweet: TweetTicket) {
              //  userName.text = tweet.id.toString()
            //    picturePath.imag = tweet.firstName
             //   tweetDate.text = tweet.lastName
              //  this.tweet.text = tweet.tweetText
            }
        }
    }

}