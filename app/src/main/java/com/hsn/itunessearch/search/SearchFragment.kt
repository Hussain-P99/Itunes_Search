package com.hsn.itunessearch.search

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hsn.itunessearch.adapter.TrackAdapter
import com.hsn.itunessearch.database.ItunesDatabase
import com.hsn.itunessearch.databinding.FragmentSearchBinding
import com.hsn.itunessearch.repository.ItuneSearch
import com.hsn.itunessearch.util.RecyclerViewDecorator


class SearchFragment : Fragment() {


    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var connectionManager: ConnectivityManager
    private val adapter = TrackAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        connectionManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val repo = ItuneSearch(ItunesDatabase.getInstance(requireContext()).itunesDao)
        val db = ItunesDatabase.getInstance(requireContext()).itunesDao
        val factory = SearchFactory(repo, db)

        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        binding.viewModel = viewModel

        binding.searchBox.setEndIconOnClickListener {
            binding.searchText.text?.clear()
        }

        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.i("debug", "Search Initiated")
                val inputMethodManager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                // initiate search
                val term = binding.searchText.text.toString()
                adapter.tracks = listOf()
                if (connectionManager.activeNetworkInfo?.isConnected == true) {
                    viewModel.search(term)
                } else {
                    viewModel.getSearchResult(term)
                }

                true

            } else {
                false
            }
        }


        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.searchRecycler.adapter = adapter
        binding.searchRecycler.addItemDecoration(RecyclerViewDecorator(8))

        viewModel.trackList.observe(viewLifecycleOwner, Observer {
            viewModel.showProgress(false)

            if (it.isEmpty()) {
                viewModel.showEmpty(true)
                return@Observer
            }

            Log.i("debug", "$it")

            viewModel.showRecyclerGrid(true)
            adapter.tracks = it
            adapter.notifyDataSetChanged()
        })
    }
}