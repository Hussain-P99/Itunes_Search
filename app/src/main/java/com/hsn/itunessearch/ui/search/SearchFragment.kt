package com.hsn.itunessearch.ui.search

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.hsn.itunessearch.R
import com.hsn.itunessearch.adapter.TrackAdapter
import com.hsn.itunessearch.adapter.TrackAdapterInterface
import com.hsn.itunessearch.database.ItunesDatabase
import com.hsn.itunessearch.database.Track
import com.hsn.itunessearch.databinding.FragmentSearchBinding
import com.hsn.itunessearch.repository.ItuneSearch
import com.hsn.itunessearch.util.RecyclerViewDecorator


class SearchFragment : Fragment(), TrackAdapterInterface {


    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var connectionManager: ConnectivityManager
    private val adapter = TrackAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        connectionManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val repo = ItuneSearch(ItunesDatabase.getInstance(requireContext()).itunesDao)
        val db = ItunesDatabase.getInstance(requireContext()).itunesDao
        val factory = SearchFactory(repo, db)

        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        binding.viewModel = viewModel


        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.i("debug", "Search Initiated")
                searchItunes()
                true
            } else {
                false
            }
        }



        binding.searchButton.setOnClickListener {
            searchItunes()
        }

        setupRecyclerView()

        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun searchItunes() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        // initiate search
        val term = binding.searchText.text.toString()
        adapter.submitList(listOf())
        if (connectionManager.activeNetworkInfo?.isConnected == true) {
            viewModel.search(term)
            binding.root.clearFocus()
        } else {
            viewModel.getSearchResult(term)
        }
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
            adapter.submitList(it)
        })
    }

    override fun onClick(view: View, track: Track) {
        val destinationTransitionName = getString(R.string.detail_transition)
        val extras = FragmentNavigatorExtras(view to destinationTransitionName)
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.anim_med).toLong()
        }

        returnTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.anim_med).toLong()
        }
        findNavController().navigate(
            SearchFragmentDirections.actionGlobalDetailsFragment(track),
            extras
        )
    }
}