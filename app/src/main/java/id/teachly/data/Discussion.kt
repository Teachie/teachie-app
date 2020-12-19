package id.teachly.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Discussion(
    @DocumentId
    val discussionId: String? = null,
    val writerId: String? = null,
    val storyId: String? = null,
    val title: String? = null,
    val question: String? = null,
    val img: String? = null,
    val category: List<String>? = null,
    val time: Timestamp? = Timestamp.now(),
    val responses: Int? = 0,
)

data class Response(
    @DocumentId
    val responseId: String? = null,
    val discussionId: String? = null,
    val writerId: String? = null,
    val comment: String? = null,
    val time: Timestamp? = Timestamp.now()
)
