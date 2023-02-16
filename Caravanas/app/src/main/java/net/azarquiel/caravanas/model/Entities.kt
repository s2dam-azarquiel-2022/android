package net.azarquiel.caravanas.model

import com.google.gson.annotations.SerializedName

data class Communities (
    @SerializedName("comunidades") val data: List<Community>,
)

data class Community (
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val name: String,
)