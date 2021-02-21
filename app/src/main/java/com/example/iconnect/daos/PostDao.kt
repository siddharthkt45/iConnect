package com.example.iconnect.daos

import com.example.iconnect.models.Post
import com.example.iconnect.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {

    val db = FirebaseFirestore.getInstance()
    val postCollections = db.collection("posts")
    val auth = Firebase.auth

    fun addPost(text: String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = Post(text, user, currentTime)
            postCollections.document().set(post)
        }
    }

    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return postCollections.document(postId).get()
    }

    fun updateLikes(postId: String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            val isLiked = post.likedBy.contains(currentUserId)

            if(isLiked) {
                post.likedBy.remove(currentUserId)
            } else {
                post.likedBy.add(currentUserId)
            }
            postCollections.document(postId).set(post)
        }
    }
}

//import com.example.iconnect.models.Post
//import com.example.iconnect.models.User
//import com.google.android.gms.tasks.Task
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.firestore.DocumentSnapshot
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.ktx.Firebase
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//
//class PostDao {
//    val db = FirebaseFirestore.getInstance()
//    val postCollections = db.collection("posts")
//    val auth = Firebase.auth
//
//    fun addPost(text: String) {                     // Function to add the post to feed
//        GlobalScope.launch {                        // Since we're doing tasks here, we've to use coroutines
//            val currentUserId = auth.currentUser!!.uid      // Extracting the current user's id using Firebase auth
//
//            val userDao = UserDao()                 // Instantiating the UserDao class (making an object of the UserDao class
//
//            val user = userDao.getUserById(currentUserId)   // Getting the user id by using the object of UserDao class
//                            .await()                        // and getUserById method and then convert the task
//                            .toObject(User::class.java)!!   // to object by using the toObject method
//
//            val currentTime = System.currentTimeMillis()    // Getting the current time from the System in milliseconds,
//                                                            // Long data type, later we'll convert it to the desired format
//
//            val post = Post(text, user, currentTime)    // Instantiating the Post class (making an object of the Post class)
//
//            postCollections.document().set(post)        // Passing the post to the postCollections database as a document
//        }
//    }
//
//    private fun getPostById(postId: String): Task<DocumentSnapshot> {
//        return postCollections.document(postId).get()
//    }
//
//    fun updateLikes(postId: String) {
//        GlobalScope.launch {
//            val currentUserId = auth.currentUser!!.uid
//            val post = getPostById(postId).await().toObject(Post::class.java)!!
//            val isLiked = post.likedBy.contains(currentUserId)
//
//            if (isLiked) {
//                post.likedBy.remove(currentUserId)
//            } else {
//                post.likedBy.add(currentUserId)
//            }
//            postCollections.document(postId).set(post)
//        }
//    }
//}