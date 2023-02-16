package net.azarquiel.caravanas.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Communities (
    @SerializedName("comunidades") @JvmField val data: List<Community>,
)

data class Community (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nombre") @JvmField val name: String,
) : Serializable

data class Provinces (
    @SerializedName("provincias") @JvmField val data: List<Province>,
)

data class Province (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nombre") @JvmField val name: String,
) : Serializable

data class Towns (
    @SerializedName("municipios") @JvmField val data: List<Town>,
)

data class Town (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nombre") @JvmField val name: String,
    @SerializedName("latitud") @JvmField val lat: String,
    @SerializedName("longitud") @JvmField val lon: String,
)