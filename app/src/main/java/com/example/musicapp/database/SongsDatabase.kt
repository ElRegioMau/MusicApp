package com.example.musicapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicapp.domain.DomainSongs


@Database(
    entities = [DomainSongs::class],
    version = 1
)
abstract class SongsDatabase: RoomDatabase() {

    abstract fun getSongsDAO(): SongsDAO

}