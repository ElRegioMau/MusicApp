package com.example.musicapp.rest

import com.example.musicapp.model.Music
import com.example.musicapp.model.MusicResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface musicService {

    @GET("search")
    fun getRockSongs(
        @Query("term") term: String,
        @Query("amp;media") media: String,
        @Query("amp;entity") entity: String,
        @Query("amp;limit") limit: Int

        ): Single<Music>
    @GET("search")
    fun getPopSongs(
        @Query("term") term: String,
        @Query("amp;media") media: String,
        @Query("amp;entity") entity: String,
        @Query("amp;limit") limit: Int

    ): Single<Music>
    @GET("search")
    fun getClassicSongs(
        @Query("term") term: String,
        @Query("amp;media") media: String,
        @Query("amp;entity") entity: String,
        @Query("amp;limit") limit: Int

    ): Single<Music>


    companion object{

        const val BASE_URL="https://itunes.apple.com/"

       //private const val ROCK_PATH ="?term=rock&amp;media=music&amp;entity=song&amp;limit=50"
        //private const val POP_PATH="?term=classick&amp;media=music&amp;entity=song&amp;limit=50"
        //private const val CLASSIC_PATH="?term=classick&amp;media=music&amp;entity=song&amp;limit=50"
    }


}