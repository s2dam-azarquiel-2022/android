package net.azarquiel.chistes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nick") @JvmField val nick: String,
)

data class Category (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nombre") @JvmField val name: String,
) : Serializable

data class Joke (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("contenido") @JvmField val text: String,
) : Serializable

data class Rating(
    @SerializedName("idchiste") @JvmField val id: String,
    @SerializedName("puntos") @JvmField val rating: Int,
)
