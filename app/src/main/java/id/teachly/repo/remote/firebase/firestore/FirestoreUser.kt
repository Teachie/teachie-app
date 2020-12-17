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
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d(TAG, "getUserById: failed error = ${error.message}")
                }

                if (value != null && value.exists()) {
                    Log.d(TAG, "getUserById: ${value.toObject(Users::class.java)}")
                    onResult(value.toObject(Users::class.java) ?: Users())
                } else {
                    Log.d(TAG, "getUserById: failed = ${error?.message}")
                    onResult(Users())
                }
            }
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

    fun updateUser(users: Users, isSuccess: (Boolean) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.USER)
            .document(Auth.getUserId() ?: "")
            .update(
                mapOf(
                    "username" to users.username,
                    "fullName" to users.fullName,
                    "bio" to users.bio,
                    "date" to users.date
                )
            )
            .addOnSuccessListener { isSuccess(true) }
            .addOnFailureListener {
                isSuccess(true)
                Log.d(TAG, "updateUser: failed = ${it.message}")
            }
    }

    fun updateStatusCreator(isSuccess: (Boolean) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.USER)
            .document(Auth.getUserId() ?: "")
            .update("creator", true)
            .addOnSuccessListener { isSuccess(true) }
            .addOnFailureListener {
                isSuccess(false)
                Log.d(TAG, "updateStatusCreator: failed = ${it.message}")
            }
    }
}