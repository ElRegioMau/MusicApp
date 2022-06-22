package com.example.musicapp.presenter

import com.examle.musicapp.database.LocalDataRepository
import com.example.musicapp.domain.DomainSongs
import com.example.musicapp.rest.MusicRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


import com.example.musicapp.domain.mapToDomainSong

class RockPresenter @Inject constructor(
    private val styleRepository: MusicRepository,
    private val compositeDisposable: CompositeDisposable,
    private val localSongsRepository: LocalDataRepository
) : RockPresenterContract {

    private var songsViewContract: RockViewContract? = null

    override fun init(viewContract: RockViewContract) {
        songsViewContract = viewContract
    }

    override fun registerToNetworkState() {
        styleRepository.checkNetworkAvailability()
    }

    override fun getRockSongs() {
        songsViewContract?.loadingSongs(true   )
        getSongsFromNetwork()
    }

    private fun insertSongsDb(){
        styleRepository.getRockSongs()
            .subscribeOn(Schedulers.io())
            .flatMapCompletable { results->
                results.results?.let { localSongsRepository.insertSongs(it.mapToDomainSong()) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                },
                { error ->
                    songsViewContract?.error(error)
                }
            ).also {
                compositeDisposable.add(it)
            }
    }

    private fun getSongsFromNetwork(){
        styleRepository.getRockSongs()
            .subscribeOn(Schedulers.io())

            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { songs->
                    songsViewContract?.successSongsResponse(songs.results?.mapToDomainSong() as MutableList<DomainSongs>)
                    insertSongsDb()
                },
                { error ->
                    songsViewContract?.error(error)
                    getSongsFromDb()
                }
            ).also {
                compositeDisposable.add(it)
            }
    }

    private fun getSongsFromDb() {
        localSongsRepository.getSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { songsViewContract?.successSongsResponse(it, true) },
                { songsViewContract?.error(it) }
            ).also {
                compositeDisposable.add(it)
            }
    }

    override fun destroyPresenter() {
        songsViewContract = null
        compositeDisposable.dispose()
    }
}

interface RockPresenterContract {
    fun init(viewContract: RockViewContract)
    fun registerToNetworkState()
    fun getRockSongs()
    fun destroyPresenter()
}

interface RockViewContract {
    fun loadingSongs(isLoading: Boolean = false)
    fun successSongsResponse(songs: MutableList<DomainSongs>, isOffline: Boolean = false)
    fun error(error: Throwable)
}