package id.teachly.repo.remote.firebase.firestore

import android.util.Log
import id.teachly.data.Discussion
import id.teachly.data.Response
import id.teachly.utils.Const

object FirestoreDiscussion {
    const val TAG = "FirestoreDiscussion"

    fun createNewDiscussion(discussion: Discussion, isSuccess: (Boolean) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.DISCUSSION)
            .document()
            .set(discussion)
            .addOnSuccessListener { isSuccess(true) }
            .addOnFailureListener {
                isSuccess(false)
                Log.d(TAG, "createNewDiscussion: failed = ${it.message}")
            }
    }


    fun createNewResponse(response: Response) {
        FirestoreInstance.instance.collection(Const.Collection.RESPONSE)
            .document()
            .set(response)
            .addOnSuccessListener { Log.d(TAG, "createNewResponse: success") }
            .addOnFailureListener { Log.d(TAG, "createNewResponse: failed") }
    }

    fun updateResponseTotal(discussionId: String, total: Int) {
        FirestoreInstance.instance.collection(Const.Collection.DISCUSSION)
            .document(discussionId)
            .update("responses", total.plus(1))
            .addOnSuccessListener { Log.d(TAG, "updateResponseTotal: success") }
            .addOnFailureListener { Log.d(TAG, "updateResponseTotal: failed") }
    }

    fun getDiscussionByStoryId(storyId: String, isSuccess: (Boolean, List<Discussion>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.DISCUSSION)
            .whereEqualTo("storyId", storyId)
            .addSnapshotListener { value, error ->
                if (error != null) Log.d(TAG, "getDiscussionByStoryId: error = ${error.message}")

                if (value != null && !value.isEmpty) {
                    isSuccess(true, value.toObjects(Discussion::class.java))
                } else {
                    isSuccess(false, listOf())
                    Log.d(TAG, "getDiscussionByStoryId: failed = ${error?.message}")
                }
            }
    }

    fun getDiscussionByUserId(userId: String, isSuccess: (Boolean, List<Discussion>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.DISCUSSION)
            .whereEqualTo("writerId", userId)
            .whereEqualTo("storyId", null)
            .addSnapshotListener { value, error ->
                if (error != null) Log.d(TAG, "getDiscussionByUserId: error = ${error.message}")

                if (value != null && !value.isEmpty) {
                    isSuccess(true, value.toObjects(Discussion::class.java))
                } else {
                    isSuccess(false, listOf())
                    Log.d(TAG, "getDiscussionByUserId: failed = ${error?.message}")
                }
            }
    }


    fun getDiscussionByDiscussionId(
        discussionId: String,
        isSuccess: (Boolean, Discussion) -> Unit
    ) {
        FirestoreInstance.instance.collection(Const.Collection.DISCUSSION)
            .document(discussionId)
            .addSnapshotListener { value, error ->
                if (error != null) Log.d(TAG, "getDiscussionByUserId: error = ${error.message}")

                if (value != null && value.exists()) {
                    isSuccess(true, value.toObject(Discussion::class.java) ?: Discussion())
                } else {
                    isSuccess(false, Discussion())
                    Log.d(TAG, "getDiscussionByUserId: failed = ${error?.message}")
                }
            }
    }


    fun getResponseByDiscussionId(discussId: String, isSuccess: (Boolean, List<Response>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.RESPONSE)
            .whereEqualTo("discussionId", discussId)
            .addSnapshotListener { value, error ->
                if (error != null) Log.d(TAG, "getResponseByDiscussionId: error = ${error.message}")

                if (value != null && !value.isEmpty) {
                    isSuccess(true, value.toObjects(Response::class.java))
                } else {
                    isSuccess(false, listOf())
                    Log.d(TAG, "getResponseByDiscussionId: failed = ${error?.message}")
                }
            }
    }
}