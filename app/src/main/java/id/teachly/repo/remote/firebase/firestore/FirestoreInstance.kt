package id.teachly.repo.remote.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreInstance {
    val instance = FirebaseFirestore.getInstance()
}