package id.teachly.repo.remote.firebase.storage

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

object StorageInstance {

    const val TAG = "StorageInstance"
    val instance = FirebaseStorage.getInstance().reference

    fun uploadPhoto(uri: Uri, path: String, isSuccess: (Boolean, url: String) -> Unit) {
        val ref = instance.child(path)
        val uploadTask = ref.putFile(uri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) task.exception?.let { throw it }
            ref.downloadUrl
        }.addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    Log.d(TAG, "uploadPhoto: success= ${it.result}")
                    isSuccess(true, it.result.toString())
                }
                false -> {
                    Log.d(TAG, "uploadPhoto: failed = ${it.exception?.message}")
                    isSuccess(false, "")
                }
            }
        }
    }
}