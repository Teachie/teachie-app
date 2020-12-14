package id.teachly.repo.remote.firebase.firestore

import android.util.Log
import id.teachly.data.Category
import id.teachly.data.getNameOnly
import id.teachly.utils.Const
import id.teachly.utils.Helpers.capitalizeWords

object FirestoreCategory {

    const val TAG = "FirestoreCategory"

    fun getAllCategory(onSuccess: (List<Category>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.CATEGORY)
            .get()
            .addOnSuccessListener {
                val categories = mutableListOf<Category>()
                for (data in it) {
                    categories.add(data.toObject(Category::class.java))
                }
                onSuccess(categories)
            }
            .addOnFailureListener { Log.d(TAG, "getAllCategory: failed = ${it.message}") }
    }

    fun getNotFavoriteCategory(myCategory: List<String>, onSuccess: (List<Category>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.CATEGORY)
            .whereNotIn("name", myCategory)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "getNotFavoriteCategory: failed. ${error.message}")
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) {
                    Log.d(
                        TAG,
                        "getNotFavoriteCategory: ${
                            value.toObjects(Category::class.java).getNameOnly()
                        }"
                    )
                    onSuccess(value.toObjects(Category::class.java))
                } else {
                    Log.d(TAG, "getNotFavoriteCategory: failed = ${error?.message}")
                }
            }
    }

    fun getCategoryByName(names: List<String>, onSuccess: (List<Category>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.CATEGORY)
            .whereIn("name", names)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d(TAG, "getCategoryByName: failed = ${error.message}")
                }

                if (value != null && !value.isEmpty) {
                    Log.d(
                        TAG,
                        "getCategoryByName: ${value.toObjects(Category::class.java).getNameOnly()}"
                    )
                    onSuccess(value.toObjects(Category::class.java))
                } else {
                    Log.d(TAG, "getCategoryByName: failed = ${error?.message}")
                }
            }
    }

    fun searchCategory(query: String, onSuccess: (List<Category>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.CATEGORY)
            .orderBy("name")
            .startAt(query.capitalizeWords()).endAt(query.capitalizeWords() + "\uf8ff")
            .get()
            .addOnSuccessListener {
                val categories = mutableListOf<Category>()
                for (data in it) {
                    categories.add(data.toObject(Category::class.java))
                }
                onSuccess(categories)
            }
            .addOnFailureListener { Log.d(TAG, "searchCategory: filed = ${it.message}") }
    }
}