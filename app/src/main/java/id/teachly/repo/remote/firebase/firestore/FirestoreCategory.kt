package id.teachly.repo.remote.firebase.firestore

import android.util.Log
import id.teachly.data.Category
import id.teachly.utils.Const

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

    fun getCategoryByName(names: List<String>, onSuccess: (List<Category>) -> Unit) {
        FirestoreInstance.instance.collection(Const.Collection.CATEGORY)
            .whereIn("name", names)
            .get()
            .addOnSuccessListener {
                val categories = mutableListOf<Category>()
                for (data in it) {
                    categories.add(data.toObject(Category::class.java))
                }
                onSuccess(categories)
            }
            .addOnFailureListener { Log.d(TAG, "getCategoryByName: failed = ${it.message}") }
    }
}