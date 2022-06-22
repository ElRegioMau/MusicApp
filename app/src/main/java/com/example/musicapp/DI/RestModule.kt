package com.example.musicapp.DI

import com.example.musicapp.rest.musicService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Module

class RestModule {

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level= HttpLoggingInterceptor.Level.BODY
        }
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit=
        Retrofit.Builder()
            .baseUrl(musicService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    @Provides
    fun providesMusicService(retrofit: Retrofit):musicService=
        retrofit.create(musicService::class.java)
    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor):OkHttpClient=
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()//CHECK THIS

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable =
        CompositeDisposable()



}