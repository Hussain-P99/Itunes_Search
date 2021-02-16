package com.hsn.itunessearch.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class SearchResult(
    val results: List<Track>
)

// used as model for retrofit and room
@Entity(tableName = "track_table")
data class Track(

    @PrimaryKey(autoGenerate = false)
    val trackName: String,

    @ColumnInfo(name = "artistName")
    val artistName: String,

    @ColumnInfo(name = "albumArt")
    @Json(name = "artworkUrl100") val albumArt: String,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "genre")
    @Json(name = "primaryGenreName") val genre: String,
    val country: String,

    @Transient
    @ColumnInfo(name = "searchTerm")
    val searchTerm: String = ""

)
