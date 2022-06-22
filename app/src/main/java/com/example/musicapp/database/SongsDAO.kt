package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.domain.DomainSongs
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface SongsDAO {

    @Query("SELECT * FROM songs_table")
    fun getSongs(): Single<MutableList<DomainSongs>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(vararg songs: DomainSongs): Completable

}