package id.teachly.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Users(
    @DocumentId
    val uid: String? = null,
    val username: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val img: String? = null,
    val date: Timestamp? = null,
    val interest: List<String>? = null,
    val bio: String? = null,
    val creator: Boolean? = false
)