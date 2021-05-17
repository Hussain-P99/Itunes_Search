package com.hsn.itunessearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hsn.itunessearch.R
import com.hsn.itunessearch.database.Track
import com.hsn.itunessearch.databinding.DataItemTrackBinding

class TrackAdapter(val trackAdapterInterface: TrackAdapterInterface) :
    ListAdapter<Track, TrackViewHolder>(TrackDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.infView(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track, trackAdapterInterface)
    }
}

interface TrackAdapterInterface {
    fun onClick(view: View, track: Track)
}

class TrackDiff : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.trackName == newItem.trackName
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }

}

class TrackViewHolder(private val binding: DataItemTrackBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun infView(parent: ViewGroup): TrackViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = DataItemTrackBinding.inflate(inflater, parent, false)
            return TrackViewHolder(view)
        }
    }

    fun bind(track: Track, trackAdapterInterface: TrackAdapterInterface) {

        binding.root.transitionName = "track_${track.trackName}"
        binding.root.setOnClickListener {
            trackAdapterInterface.onClick(it, track)
        }

        Glide.with(itemView.context)
            .load(track.albumArt)
            .placeholder(R.drawable.ic_hourglass_empty)
            .error(R.drawable.ic_broken_image)
            .into(binding.albumArt)

        binding.trackName.headerName.text = itemView.resources.getString(R.string.track_name)
        binding.trackName.headerText.text = track.trackName

        binding.artistName.headerName.text = itemView.resources.getString(R.string.artist_name)
        binding.artistName.headerText.text = track.artistName

        binding.releaseDate.headerName.text = itemView.resources.getString(R.string.release_date)
        binding.releaseDate.headerText.text = track.releaseDate

        binding.country.headerName.text = itemView.resources.getString(R.string.country_name)
        binding.country.headerText.text = track.country

        binding.executePendingBindings()
    }
}