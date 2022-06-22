package com.example.musicapp.DI

import com.example.musicapp.MainActivity
import com.example.musicapp.ui.classic
import com.example.musicapp.ui.pop
import dagger.Component
import com.example.musicapp.ui.rock


@Component(
    modules = [
        ApplicationModule::class,
        RestModule::class,
        PresentersModule::class,
        RepositoryModule::class
    ]
)

interface MusicComponent {

    fun inject(MainActivity: MainActivity)
    fun inject(rockFragment: rock)
    fun inject(classicFragment: classic)
    fun inject(popFragment: pop)

}