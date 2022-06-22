package com.examle.musicapp.database

import com.example.musicapp.database.SongsDAO
import com.example.musicapp.domain.DomainSongs
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LocalDataRepository {

    fun getSongs(): Single<MutableList<DomainSongs>>
    fun insertSongs(songs: List<DomainSongs>): Completable

}

class LocalDataRepositoryImpl @Inject constructor(
    private val songsDAO: SongsDAO
) : LocalDataRepository {
    override fun getSongs(): Single<MutableList<DomainSongs>> {
        return songsDAO.getSongs()
    }

    override fun insertSongs(songs: List<DomainSongs>): Completable {
        return songsDAO.insertSongs(* songs.toTypedArray())
    }

}