package digital.sogood.livestreamfails.mobile.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@Parcelize
data class DetailsViewModel(val title: String,
                            val streamer: String?,
                            val game: String?,
                            val points: Int,
                            val nsfw: Boolean,
                            val videoUrl: String,
                            val sourceUrl: String?) : Parcelable