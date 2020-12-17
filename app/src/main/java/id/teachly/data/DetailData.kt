package id.teachly.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailData(
    val title: String? = null,
    val type: String? = null
) : Parcelable