package com.example.iconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iconnect.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener: IPostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
        options
) {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder =  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
        viewHolder.likeButton.setOnClickListener{
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = model.text
        holder.userText.text = model.createdBy.displayName
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text = model.likedBy.size.toString()
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)

        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)
        if(isLiked) {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_liked))
        } else {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_unliked))
        }
    }
}

interface IPostAdapter {
    fun onLikeClicked(postId: String)
}

//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.iconnect.models.Post
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//// Here we'll use the Recycler Adapter provided by Firestore which automatically handles the count of entries in the recyclerview
//class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener: IPostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
//    options
//) {
//    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val postText: TextView = itemView.findViewById(R.id.postTitle)      // Assigning the views to their respective
//        val userText: TextView = itemView.findViewById(R.id.userName)       // positions
//        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
//        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
//        val userImage: ImageView = itemView.findViewById(R.id.userImage)
//        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
//        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context)    // return an instance of PostViewHolder which will
//                                            .inflate(R.layout.item_post, parent, false))    // inflate the recyclerview
//        viewHolder.likeButton.setOnClickListener {
//            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
//        }
//        return viewHolder
//    }
//
//    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
//        holder.postText.text = model.text                   // Setting postTitle to the postTitle TextView
//
//        holder.userText.text = model.createdBy.displayName  // Setting userName to the UserName TextView
//
//        Glide.with(holder.userImage.context)                // Giving context of userImage to load the user's
//            .load(model.createdBy.imageUrl)                 // profile image using Glide library through a URI
//            .circleCrop()                                   // Makes the profile image circular in shape
//            .into(holder.userImage)                         // Shows where to hold the image
//
//        holder.likeCount.text = model.likedBy.size.toString()   // Setting the count of likes to the likeCount TextView
//                                                                // after extracting it from the "likedBy" ArrayList
//
//        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)   // Setting the time in the desired format
//
//        val auth = Firebase.auth
//        val currentUserId = auth.currentUser!!.uid
//        val isLiked = model.likedBy.contains(currentUserId)
//        if (isLiked) {
//            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_liked))
//        } else {
//            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_unliked))
//        }
//
//    }
//}
//
//interface IPostAdapter {
//    fun onLikeClicked(postId: String)
//}