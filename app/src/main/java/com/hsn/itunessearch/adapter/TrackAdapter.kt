package com.hsn.itunessearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hsn.itunessearch.database.Track
import com.hsn.itunessearch.databinding.DataItemTrackBinding

class TrackAdapter : RecyclerView.Adapter<TrackViewHolder>() {

    var tracks = listOf<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.infView(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
    }

    override fun getItemCount(): Int = tracks.size
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

    fun bind(track: Track) {
        binding.track = track
    }
}