package net.azarquiel.caravanas.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

abstract class DataHolder<T> { abstract val data: T }

data class Communities ( @SerializedName("comunidades") override val data: List<Community> ) : DataHolder<List<Community>>()
data class Provinces ( @SerializedName("provincias") override val data: List<Province> ) : DataHolder<List<Province>>()
data class Towns ( @SerializedName("municipios") override val data: List<Town> ) : DataHolder<List<Town>>()
data class Parkings ( @SerializedName("lieux") override val data: List<Parking> ) : DataHolder<List<Parking>>()
data class Photos ( @SerializedName("p4n_photos") override val data: List<Photo> ) : DataHolder<List<Photo>>()
data class AvgRate ( @SerializedName("avg") override val data: Float ) : DataHolder<Float>()
data class User ( @SerializedName("usuario") override val data: UserData ) : DataHolder<UserData?>()

data class Community (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nombre") @JvmField val name: String,
) : Serializable

data class Province (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nombre") @JvmField val name: String,
) : Serializable

data class Town (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nombre") @JvmField val name: String,
    @SerializedName("latitud") @JvmField val lat: String,
    @SerializedName("longitud") @JvmField val lon: String,
) : Serializable

data class Parking (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("titre") @JvmField val name: String,
    @SerializedName("description_es") @JvmField val desc: String,
) : Serializable

data class Photo (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("link_large") @JvmField val photo: String,
)

data class UserData (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nick") @JvmField val nick: String,
    @SerializedName("pass") @JvmField val pass: String,
)