package com.example.musicapp.rest

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.musicapp.model.Music
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


interface MusicRepository {

    fun getRockSongs():Single<Music>
    fun getPopSongs():Single<Music>
    fun getClassicSongs():Single<Music>
    fun checkNetworkAvailability()
    val networkState : BehaviorSubject<Boolean>

}

class MusicRepositoryIml @Inject constructor(

    private val service: musicService

): MusicRepository{
    private val connectivityManager: ConnectivityManager?
        get() = connectivityManager
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

   override val networkState : BehaviorSubject<Boolean> = BehaviorSubject.create()

   override fun checkNetworkAvailability() {
        connectivityManager?.requestNetwork(networkRequest,object :
            ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkState.onNext(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                networkState.onNext(false)

            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkState.onNext(false)
            }
        })}

    override fun getRockSongs(): Single<Music> {
        return service.getRockSongs("rock","music","song",50)
    }
    override fun getPopSongs(): Single<Music> {
        return service.getPopSongs("pop","music","song",50)
    }
    override fun getClassicSongs(): Single<Music> {
        return service.getClassicSongs("classic","music","song",50)
    }
}



