package com.example.musicapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicapp.model.MusicResult

@Entity(tableName = "songs_table")
class DomainSongs (
    @PrimaryKey
    val artistId: Int,
    val songId: Int,
    val artistsName: String,
    val collectionName: String,
    val artWorkUrl60: String,
    val trackPrice: Double,

)

fun List<MusicResult?>.mapToDomainSong(): List<DomainSongs> {
    return this.map { DomainSong ->
        DomainSongs(
            artistId = DomainSong?.artistId ?: 0,
            songId = DomainSong?.trackId ?: 0,
            artistsName = DomainSong?.artistName ?: "Unknown Artist",
            collectionName = DomainSong?.collectionName ?: "Unknown Collection",
            artWorkUrl60 = DomainSong?.artworkUrl60 ?: "Unknown Image",
            trackPrice = DomainSong?.trackPrice ?: 00000.00

        )
    }

}