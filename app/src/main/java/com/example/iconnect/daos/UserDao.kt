package com.example.iconnect.daos

import com.example.iconnect.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    private val db = FirebaseFirestore.getInstance()                // FirebaseFirestore instance created as database

    private val usersCollection = db.collection("users") // since a database can have multiple collection,
                                                                    // we're creating a usersCollection here which will
                                                                    // store users

    fun addUser(user: User?) {                    // function to add a user
        user?.let {                               // ?. checks if the variable is null -> terminate otherwise enter the scope
            GlobalScope.launch(Dispatchers.IO) {                // this is to ensure that the database operations
                usersCollection.document(user.uid).set(it)      // run in a different thread from that of the activities
            }                                                   // and do not make the app laggy
        }
    }
}