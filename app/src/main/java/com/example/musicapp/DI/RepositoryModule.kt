package com.example.musicapp.DI

import com.examle.musicapp.database.LocalDataRepository
import com.examle.musicapp.database.LocalDataRepositoryImpl
import com.example.musicapp.rest.MusicRepository
import com.example.musicapp.rest.MusicRepositoryIml
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideLocalRepository(
        localDataRepositoryImpl: LocalDataRepositoryImpl
    ): LocalDataRepository

    @Binds
    abstract fun providesNetworkRepository(
        networkRepositoryImpl: MusicRepositoryIml
    ) : MusicRepository

}