package com.example.ongraphtask6.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.example.ongraphtask6.R
import com.example.ongraphtask6.retrofit.models.view_feed.SingleFeedPost

class FeedAdapter(val dataset : MutableList<SingleFeedPost> , val context : Context) : RecyclerView.Adapter<FeedAdapter.MyViewHolder>() {

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val personName : TextView = view.findViewById(R.id.postCreatorName)
        val description : TextView = view.findViewById(R.id.caption)
        val personName2 : TextView = view.findViewById(R.id.profilename2)
        val personDP : ImageView = view.findViewById(R.id.uploaderDPImage)
        val postImage : ImageView = view.findViewById(R.id.postImage)
        val likes : TextView = view.findViewById(R.id.feedLikesTV)
        val comments : TextView = view.findViewById(R.id.feedCommentsTV)
        val shares : TextView = view.findViewById(R.id.feedSharesTV)
        val time : TextView = view.findViewById(R.id.feedtimetv)
        //val hashtags : TextView = view.findViewById(R.id.has)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item , parent , false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.personName.text = dataset[position].posterName
       holder.personName2.text = dataset[position].posterName
       holder.description.text = dataset[position].content
       holder.likes.text = dataset[position].likes
       holder.comments.text = dataset[position].comments
       holder.shares.text = dataset[position].shares
       holder.time.text = dataset[position].time
       //holder.hashtags.text = dataset[position].hashtag

        ZoomHelper.addZoomableView(holder.postImage)
        ZoomHelper.addZoomableView(holder.personDP)

        Glide.with(context)
            .load(dataset[position].posterDP)
            .fitCenter()
            .circleCrop()
            .into(holder.personDP)

        Glide.with(context)
            .load(dataset[position].postImage)
            .centerCrop()
            .into(holder.postImage)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}