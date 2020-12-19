package id.teachly.data

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Space(
    @DocumentId
    val spaceId: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val desc: String? = null,
    val img: String? = null,
    val category: List<String>? = null,
    val section: Int? = 0
) : Parcelable
