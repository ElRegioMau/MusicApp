package com.example.musicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.databinding.CardViewBinding
import com.example.musicapp.domain.DomainSongs
import com.squareup.picasso.Picasso

class MusicAdapter(
    private val onSongClicked: SongClickListener,
    private val songsData: MutableList<DomainSongs> = mutableListOf()

) : RecyclerView.Adapter<SongViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder = SongViewHolder(
        CardViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songsData[position],onSongClicked)

    }

    override fun getItemCount(): Int = songsData.size


}

class SongViewHolder(private val binding: CardViewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(song: DomainSongs, onSongClicked:SongClickListener) {//check this private val onSongClicked: songClickListener,
        binding.Artist.text = song.artistsName ?: "N/A"
        binding.Song.text = song.collectionName ?: "N/A"
        binding.Price.text = song.trackPrice.toString() ?: "N/A"


        Picasso.get()
            .load(song.artWorkUrl60)
            .fit()
            .into(binding.Image)

        itemView.setOnClickListener {
            onSongClicked.onSongRClicked(song)
        }
    }
}
interface SongClickListener{
    fun onSongRClicked(song: DomainSongs)
}
