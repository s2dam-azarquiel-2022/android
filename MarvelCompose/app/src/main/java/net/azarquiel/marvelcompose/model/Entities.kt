package net.azarquiel.marvelcompose.model

import com.google.gson.annotations.SerializedName

data class UserData (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nick") @JvmField val nick: String,
)

data class MarvelImg (
    @SerializedName("path") @JvmField val url: String,
    @SerializedName("extension") @JvmField val extension: String,
)

data class Hero (
    @SerializedName("id") @JvmField val id: Long,
    @SerializedName("name") @JvmField val name: String,
    @SerializedName("description") @JvmField val description: String,
    @SerializedName("thumbnail") @JvmField val img: MarvelImg,
)
