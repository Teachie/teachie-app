package id.teachly.repo.remote.firebase.firestore

import android.util.Log
import id.teachly.data.Space
import id.teachly.utils.Const

object FirestoreSpace {

    const val TAG = "FirestoreSpace"

    fun createNewSpace(space: Space, isSuccess: (Boolean) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.SPACE)
            .document()
            .set(space)
            .addOnSuccessListener { isSuccess(true) }
            .addOnFailureListener {
                isSuccess(false)
                Log.d(TAG, "createNewSpace: failed = $it")
            }
    }

    fun geSpaceByUserId(uid: String, isSuccess: (Boolean, List<Space>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.SPACE)
            .whereEqualTo("userId", uid)
            .addSnapshotListener { value, error ->
                if (error != null) Log.d(TAG, "geSpaceByUserId: error = ${error.message}")

                if (value != null && !value.isEmpty) {
                    isSuccess(true, value.toObjects(Space::class.java))
                } else {
                    Log.d(TAG, "geSpaceByUserId: failed = ${error?.message}")
                    isSuccess(false, listOf())
                }
            }
    }
}