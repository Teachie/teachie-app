package id.teachly.repo.remote.firebase.firestore

import android.util.Log
import id.teachly.data.Users
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.utils.Const

object FirestoreUser {

    private const val TAG = "FirestoreUser"

    fun createNewUser(users: Users, isSuccess: (Boolean) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.USER)
            .document(Auth.getUserId() ?: "")
            .set(users)
            .addOnSuccessListener { isSuccess(true) }
            .addOnFailureListener {
                Log.d(TAG, "createNewUser: failed = ${it.message}")
                isSuccess(false)
            }
    }

    fun updatePhotoUser(img: String, isSuccess: (Boolean) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.USER)
            .document(Auth.getUserId() ?: "")
            .update("img", img)
            .addOnSuccessListener { isSuccess(true) }
            .addOnFailureListener {
                Log.d(TAG, "updatePhotoUser: failed = ${it.message}")
                isSuccess(false)
            }

    }

    fun getUserById(userId: String, onResult: (Users) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.USER)
            .document(userId)
            .get()
            .addOnSuccessListener {
                onResult(it.toObject(Users::class.java) ?: Users())
            }
            .addOnFailureListener { Log.d(TAG, "getUserById: failed = ${it.message}") }
    }

    fun getUserByUsername(username: String, onResult: (Users) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.USER)
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener {
                val users = mutableListOf<Users>()
                for (data in it) users.add(data.toObject(Users::class.java))
                onResult(if (users.size != 0) users[0] else Users())
            }
            .addOnFailureListener { Log.d(TAG, "getUserByUsername: failed = ${it.message}") }
    }
}