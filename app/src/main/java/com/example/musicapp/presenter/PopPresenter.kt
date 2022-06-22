package com.example.musicapp.presenter

import com.examle.musicapp.database.LocalDataRepository
import com.example.musicapp.domain.DomainSongs
import com.example.musicapp.rest.MusicRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


import com.example.musicapp.domain.mapToDomainSong


class PopPresenter @Inject constructor(
    private val styleRepository: MusicRepository,
    private val compositeDisposable: CompositeDisposable,
    private val localSongsRepository: LocalDataRepository
) : PopPresenterContract {

    private var songsViewContract: PopViewContract? = null

    override fun init(viewContract: PopViewContract) {
        songsViewContract = viewContract
    }

    override fun registerToNetworkState() {
        styleRepository.checkNetworkAvailability()
    }

    override fun getPopSongs() {
        songsViewContract?.loadingSongs(true)
        getSongsFromNetwork()
    }

    private fun insertSongsDb(){
        styleRepository.getPopSongs()
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
        styleRepository.getPopSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    songsViewContract?.successSongsResponse(it.results?.mapToDomainSong() as MutableList<DomainSongs>)
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

interface PopPresenterContract {
    fun init(viewContract: PopViewContract)
    fun registerToNetworkState()
    fun getPopSongs()
    fun destroyPresenter()
}

interface PopViewContract {
    fun loadingSongs(isLoading: Boolean = false)
    fun successSongsResponse(songs: MutableList<DomainSongs>, isOffline: Boolean = false)
    fun error(error: Throwable)
}
