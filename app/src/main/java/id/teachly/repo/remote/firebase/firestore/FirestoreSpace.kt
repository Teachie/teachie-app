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

    fun getSpaceByUserId(uid: String, isSuccess: (Boolean, List<Space>) -> Unit) {
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

    fun getSpaceById(spaceId: String, isSuccess: (Boolean, Space) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.SPACE)
            .document(spaceId)
            .get()
            .addOnSuccessListener { isSuccess(true, it.toObject(Space::class.java) ?: Space()) }
            .addOnFailureListener {
                isSuccess(false, Space())
                Log.d(TAG, "getSpaceById: failed = ${it.message}")
            }
    }

    fun updateSpaceById(space: Space) {
        FirestoreInstance.instance.collection(Const.Collection.SPACE)
            .document(space.spaceId ?: "")
            .update("section", space.section?.plus(1))
            .addOnSuccessListener { Log.d(TAG, "updateSpaceById: success") }
            .addOnFailureListener { Log.d(TAG, "updateSpaceById: failed") }
    }
}