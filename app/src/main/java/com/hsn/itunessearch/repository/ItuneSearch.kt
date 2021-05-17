package com.hsn.itunessearch.repository

import android.util.Log
import com.hsn.itunessearch.ItunesApi
import com.hsn.itunessearch.database.ItunesDao
import com.hsn.itunessearch.database.SearchResult
import com.hsn.itunessearch.database.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItuneSearch(private val localdb: ItunesDao) {

    suspend fun searchTrack(searchTerm: String) {

        val listTrack = ItunesApi.retrofitService.Search(searchTerm)

        Log.i("debug", "$listTrack")
        updateListToDb(listTrack, searchTerm)
    }

    suspend fun updateListToDb(result: SearchResult, searchTerm: String) =
        withContext(Dispatchers.IO) {
            val tracks = mutableListOf<Track>()
            result.results.forEach {
                tracks.add(
                    Track(
                        trackName = it.trackName,
                        artistName = it.artistName,
                        albumArt = it.albumArt,
                        releaseDate = it.releaseDate,
                        genre = it.genre,
                        country = it.country,
                        searchTerm = searchTerm,
//                        contentRating = it.contentRating,
//                        description = it.description,
                    )
                )
            }
            localdb.insertAll(tracks)
        }
}
