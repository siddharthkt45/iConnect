package com.example.iconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iconnect.daos.PostDao
import com.example.iconnect.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var postDao: PostDao
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener{
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollections
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}

//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
////import android.view.MenuItem
////import androidx.annotation.NonNull
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.iconnect.daos.PostDao
//import com.example.iconnect.models.Post
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
////import com.google.android.gms.auth.api.signin.GoogleSignInClient
////import com.google.android.gms.tasks.OnCompleteListener
////import com.google.android.gms.tasks.Task
//import com.google.firebase.firestore.Query
////import com.google.firebase.ktx.Firebase
//import kotlinx.android.synthetic.main.activity_main.*
//
//class MainActivity : AppCompatActivity(), IPostAdapter {
//
////    private lateinit var mGoogleSignInClient: GoogleSignInClient
//    private lateinit var postDao: PostDao
//    private lateinit var adapter: PostAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        fab.setOnClickListener {
//            val intent = Intent(this, CreatePostActivity::class.java)
//            startActivity(intent)
//        }
//
//        setUpRecyclerView()
//    }
//
//    private fun setUpRecyclerView() {
//        postDao = PostDao()                                 // Instantiating PostDao class
//        val postsCollections = postDao.postCollections      // Collecting the post from the postCollections property
//
//
//        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
//
//        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>()
//                                                        .setQuery(query, Post::class.java)
//                                                        .build()
//
//        adapter = PostAdapter(recyclerViewOptions, this)
//
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }
//
//    override fun onLikeClicked(postId: String) {
//        postDao.updateLikes(postId)
//    }
//
////    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
////        R.id.action_sign_out -> {
////            true
////        }
////
////        else -> {
////            // If we got here, the user's action was not recognized.
////            // Invoke the superclass to handle it.
////            super.onOptionsItemSelected(item)
////        }
////    }
//
////    fun signOut(item: MenuItem) {
////        logout()
////    }
//
////    private fun logout() {
////        mGoogleSignInClient.signOut()
////            .addOnCompleteListener(this) {
////                fun onComplete(task: Task<Void>) {
////                    val intent = Intent(this, SignInActivity::class.java)
////                    startActivity(intent)
////                }
////            }
////    }
//}