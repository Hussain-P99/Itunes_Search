package com.hsn.itunessearch.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import com.hsn.itunessearch.R
import com.hsn.itunessearch.database.Track
import com.hsn.itunessearch.databinding.FragmentDetailBinding
import com.hsn.itunessearch.util.themeColor

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var track: Track? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentHost
            duration = resources.getInteger(R.integer.anim_med).toLong()
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        track = DetailsFragmentArgs.fromBundle(requireArguments()).trackDetails

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load(track?.albumArt)
            .placeholder(R.drawable.ic_hourglass_empty)
            .error(R.drawable.ic_broken_image)
            .into(binding.ilAlbumArt)

        binding.ilTrackName.headerName.text = getString(R.string.track_name)
        binding.ilTrackName.headerText.text = track?.trackName

        binding.ilArtistName.headerName.text = getString(R.string.artist_name)
        binding.ilArtistName.headerText.text = track?.artistName

        binding.ilReleaseDate.headerName.text = getString(R.string.release_date)
        binding.ilReleaseDate.headerText.text = track?.releaseDate

        binding.ilCountry.headerName.text = getString(R.string.country_name)
        binding.ilCountry.headerText.text = track?.country

        binding.ilDescription.headerName.text = getString(R.string.description)
//        binding.ilDescription.headerName.text = track?.description

        binding.ilGenre.headerName.text = getString(R.string.genre)
        binding.ilGenre.headerText.text = track?.genre

        binding.ilContentRating.headerName.text = getString(R.string.rating)
//        binding.ilContentRating.headerName.text = track?.contentRating

    }
}