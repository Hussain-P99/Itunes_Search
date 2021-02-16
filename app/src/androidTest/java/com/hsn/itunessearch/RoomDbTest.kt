package com.hsn.itunessearch

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hsn.itunessearch.database.ItunesDao
import com.hsn.itunessearch.database.ItunesDatabase
import com.hsn.itunessearch.database.Track
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException


@RunWith(JUnit4::class)
class RoomDbTest {

    private lateinit var itunesDao: ItunesDao
    private lateinit var memDb: ItunesDatabase

    private val testItem = Track(
        "x",
        "y",
        "z",
        "a",
        "b",
        "d"
    )

    @Before
    fun setupDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        memDb = Room.inMemoryDatabaseBuilder(
            context, ItunesDatabase::class.java
        ).build()

        itunesDao = memDb.itunesDao

    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        memDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun RWtest() {
        insertTest()
        deleteTest()
    }


    @Test
    fun insertTest() {
        itunesDao.insert(testItem)

        val item = itunesDao.getTrack(testItem.trackName)

        assertNotEquals(item, null)
        assertEquals(item?.trackName, testItem.trackName)
    }

    @Test
    fun deleteTest() {
        itunesDao.delete(testItem)

        val item = itunesDao.getTrack(testItem.trackName)

        assertEquals(item, null)
    }
}