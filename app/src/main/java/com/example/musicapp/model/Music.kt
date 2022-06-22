package com.example.musicapp.model


import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("resultCount")
    val resultCount: Int?,
    @SerializedName("results")
    val results: List<MusicResult?>?
)