package id.teachly.repo.remote.firebase.storage

import android.net.Uri
import com.google.firebase.Timestamp
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
        StorageInstance.uploadPhoto(img, path) { b, link -> isSuccess(b, link) }
    }

    fun storeImgStory(img: Uri, isSuccess: (Boolean, url: String) -> Unit) {
        val path = buildString {
            append(Const.Storage.IMAGE)
            append(Const.Storage.STORY)
            append("${Auth.getUserId()}/")
            append(Timestamp.now())
            append(Const.Storage.JPG)
        }
        StorageInstance.uploadPhoto(img, path) { b, link -> isSuccess(b, link) }
    }

    fun storeImgSpace(img: Uri, isSuccess: (Boolean, url: String) -> Unit) {
        val path = buildString {
            append(Const.Storage.IMAGE)
            append(Const.Storage.SPACE)
            append("${Auth.getUserId()}/")
            append(Timestamp.now())
            append(Const.Storage.JPG)
        }
        StorageInstance.uploadPhoto(img, path) { b, link -> isSuccess(b, link) }
    }

    fun storeImgDiscussion(img: Uri, isSuccess: (Boolean, url: String) -> Unit) {
        val path = buildString {
            append(Const.Storage.IMAGE)
            append(Const.Storage.DISCUSSION)
            append("${Auth.getUserId()}/")
            append(Timestamp.now())
            append(Const.Storage.JPG)
        }
        StorageInstance.uploadPhoto(img, path) { b, link -> isSuccess(b, link) }
    }
}