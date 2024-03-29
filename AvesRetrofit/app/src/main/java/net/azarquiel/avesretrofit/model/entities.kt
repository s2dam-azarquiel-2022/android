package net.azarquiel.avesretrofit.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Zones (
    @SerializedName("zonas") @JvmField val zones: List<Zone>,
)

data class Zone (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nombre") @JvmField val name: String,
    @SerializedName("localizacion") @JvmField val localization: String,
    @SerializedName("formaciones_principales") @JvmField val formations: String,
    @SerializedName("presentacion") @JvmField val presentation: String,
    @SerializedName("geom_lat") @JvmField val lat: String,
    @SerializedName("geom_lon") @JvmField val lon: String,
) : Serializable

data class Resources (
    @SerializedName("recursos") @JvmField val resources: List<Resource>,
)

data class Resource (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("zona") @JvmField val zone: String,
    @SerializedName("titulo") @JvmField val name: String,
    @SerializedName("url") @JvmField val image: String,
) : Serializable

data class Comments (
    @SerializedName("comentarios") @JvmField val comments: List<CommentData>,
)

data class Comment (
    @SerializedName("comentario") @JvmField val comment: CommentData,
)

data class CommentData (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("recurso") @JvmField val resource: String,
    @SerializedName("nick") @JvmField val author: String,
    @SerializedName("fecha") @JvmField val date: String,
    @SerializedName("comentario") @JvmField val comment: String,
)

data class User (
    @SerializedName("usuario") @JvmField val data: UserData?
)

data class UserData (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nick") @JvmField val nick: String,
    @SerializedName("pass") @JvmField val pass: String,
)
