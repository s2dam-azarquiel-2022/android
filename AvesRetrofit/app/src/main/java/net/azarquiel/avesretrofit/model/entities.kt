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
