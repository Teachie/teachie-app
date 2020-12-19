package id.teachly.repo.remote.firebase.firestore

import android.util.Log
import id.teachly.data.Story
import id.teachly.utils.Const

object FirestoreStory {

    const val TAG = "FirestoreStory"

    fun publishNewStory(story: Story, isSuccess: (Boolean, id: String) -> Unit) {
        val ref = FirestoreInstance.instance.collection(Const.Collection.STORY)
            .document()
        ref.set(story)
            .addOnSuccessListener { isSuccess(true, ref.id) }
            .addOnFailureListener {
                isSuccess(false, "")
                Log.d(TAG, "publishNewStory: filed = ${it.message}")
            }
    }

    fun getStoryByUserId(uid: String, onResult: (List<Story>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.STORY)
            .whereEqualTo("writerId", uid)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d(TAG, "getStoryByUserId: error = ${error.message}")
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) {
                    val data = value.toObjects(Story::class.java)
                    onResult(data)
                } else {
                    Log.d(TAG, "getStoryByUserId: ")
                    onResult(listOf())
                }
            }
    }
}