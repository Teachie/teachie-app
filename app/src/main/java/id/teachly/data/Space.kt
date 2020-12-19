package id.teachly.data

import com.google.firebase.firestore.DocumentId

data class Space(
    @DocumentId
    val spaceId: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val desc: String? = null,
    val img: String? = null,
    val category: List<String>? = null,
    val section: Int? = 0
)
