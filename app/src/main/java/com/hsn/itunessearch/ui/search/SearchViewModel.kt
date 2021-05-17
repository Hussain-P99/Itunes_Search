package com.hsn.itunessearch.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsn.itunessearch.database.ItunesDao
import com.hsn.itunessearch.database.Track
import com.hsn.itunessearch.repository.ItuneSearch
import kotlinx.coroutines.launch

class SearchViewModel(val ituneRepo: ItuneSearch, val itunesDao: ItunesDao) : ViewModel() {


    val _trackList = MutableLiveData<List<Track>>(listOf())
    val trackList: LiveData<List<Track>>
        get() = _trackList

    var showProgressBar = MutableLiveData<Boolean>(false)
        private set

    var showEmpty = MutableLiveData<Boolean>(true)
        private set


    init {
        showEmpty.value = true
        showProgressBar.value = false
    }

    fun search(term: String) {
        if (term.isEmpty()) return
        Log.i("debug", "searching $term")

        showProgress(true)
        showEmpty(false)

        viewModelScope.launch {
            try {
                ituneRepo.searchTrack(term)
                getSearchResult(term)
            } catch (e: Exception) {
                getSearchResult(term)
            }
        }
    }


    fun showProgress(visible: Boolean) {
        showProgressBar.value = visible
    }

    fun showEmpty(visible: Boolean) {
        showEmpty.value = visible
    }

    fun getSearchResult(term: String) {
        showProgress(true)
        showEmpty(false)
        viewModelScope.launch {
            _trackList.value = itunesDao.search(term)
        }
    }
}