package com.example.musicapp

import android.app.Application
import com.example.musicapp.DI.ApplicationModule
import com.example.musicapp.DI.DaggerMusicComponent
import com.example.musicapp.DI.MusicComponent

class MusicApp : Application() {

    override fun onCreate() {
        super.onCreate()

        component = DaggerMusicComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

    }

    companion object{
        lateinit var component: MusicComponent
    }


}