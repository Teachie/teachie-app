package id.teachly.repo.remote.firebase.storage

import android.net.Uri
import android.util.Log
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.utils.Const

object StorageUser {

    private const val TAG = "StorageUser"

    fun uploadPhoto(img: Uri, isSuccess: (Boolean, url: String) -> Unit) {
        val path = buildString {
            append(Const.Storage.IMAGE)
            append(Const.Storage.AVATAR)
            append(Auth.getUserId())
            append(Const.Storage.JPG)
        }
        val ref = StorageInstance.instance.child(path)
        val uploadTask = ref.putFile(img)
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