package id.teachly.repo.remote.firebase.storage

import com.google.firebase.storage.FirebaseStorage

object StorageInstance {
    val instance = FirebaseStorage.getInstance().reference
}