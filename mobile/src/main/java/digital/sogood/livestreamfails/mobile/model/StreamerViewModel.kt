package digital.sogood.livestreamfails.mobile.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@Parcelize
data class StreamerViewModel(val name: String,
                             val fails: Int,
                             val imageUrl: String) : Parcelable