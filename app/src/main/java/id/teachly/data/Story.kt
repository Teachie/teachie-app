package id.teachly.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(
    @DocumentId
    val storyId: String? = null,
    val title: String? = null,
    val content: String? = null,
    var thumbnail: String? = null,
    val categories: List<String>? = null,
    val writerId: String? = null,
    val spaceId: String? = null,
    val love: Int? = 0,
    val discuss: Int? = 0,
    val dateCreated: Timestamp? = Timestamp.now(),
) : Parcelable

data class StoryContent(
    val data: String,
    val type: Int
)
