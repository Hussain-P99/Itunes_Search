package com.hsn.itunessearch.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hsn.itunessearch.database.ItunesDao
import com.hsn.itunessearch.repository.ItuneSearch
import java.lang.IllegalArgumentException

class SearchFactory(private val ituneSearch: ItuneSearch, private val itunesDao: ItunesDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(ituneSearch, itunesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel.")
    }
}