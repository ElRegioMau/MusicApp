package com.example.musicapp.DI

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.musicapp.database.SongsDAO
import com.example.musicapp.database.SongsDatabase
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule (private val application: Application) {

    @Provides
    fun providesContext(): Context = application.applicationContext

    @Provides
    fun providesRoomDb(context: Context): SongsDatabase =
        Room.databaseBuilder(
            context,
            SongsDatabase::class.java,
            "songs_db"
        ).build()

    @Provides
    fun providesSongsDAO(database: SongsDatabase): SongsDAO =
        database.getSongsDAO()
}