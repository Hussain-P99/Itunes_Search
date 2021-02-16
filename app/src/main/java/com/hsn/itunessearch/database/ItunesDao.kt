package com.hsn.itunessearch.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ItunesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<Track>)

    @Update
    suspend fun update(track: Track)

    @Delete
    fun delete(track: Track)

    @Query("Delete from track_table")
    suspend fun deleteAll()

    @Query("Select * from track_table where searchTerm = :term")
    suspend fun search(term: String): List<Track>

    @Query("Select * from track_table where trackName = :track")
    fun getTrack(track: String): Track?

    @Query("Select * from track_table")
    fun getAll(): LiveData<List<Track>>

}