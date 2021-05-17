package com.hsn.itunessearch.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class SearchResult(
    val results: List<Track>
)

// used as model for retrofit and room
@Entity(tableName = "track_table")
@Parcelize
data class Track(

    @PrimaryKey(autoGenerate = false)
    val trackName: String,

    val artistName: String,

    @Json(name = "artworkUrl100") val albumArt: String,

    val releaseDate: String,

    @Json(name = "primaryGenreName") val genre: String,

    val country: String,

    @Transient
    @ColumnInfo(name = "searchTerm") val searchTerm: String = "",

//    @Json(name = "longDescription") val description: String,
//
//    @Json(name = "contentAdvisoryRating") val contentRating: String
) : Parcelable
