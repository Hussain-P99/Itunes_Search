package com.hsn.itunessearch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Track::class], exportSchema = false, version = 1)
abstract class ItunesDatabase : RoomDatabase() {

    abstract val itunesDao: ItunesDao


    companion object {
        @Volatile
        private var itunesDatabase: ItunesDatabase? = null

        fun getInstance(context: Context): ItunesDatabase {
            synchronized(this) {
                var db = itunesDatabase

                if (db == null) {
                    db = Room.databaseBuilder(
                        context,
                        ItunesDatabase::class.java,
                        "Itunes Database"
                    ).fallbackToDestructiveMigration().build()

                    itunesDatabase = db
                }

                return db
            }
        }
    }
}