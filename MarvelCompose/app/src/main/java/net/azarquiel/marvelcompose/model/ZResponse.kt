package net.azarquiel.marvelcompose.model

import com.google.gson.annotations.SerializedName

abstract class ZResponse<T> { abstract val data: T }

data class User ( @SerializedName("usuario") override val data: UserData? ) : ZResponse<UserData?>()
data class MarvelResults (@SerializedName("data") override val data: Heroes ) : ZResponse<Heroes>()
data class Heroes ( @SerializedName("results") override val data: List<Hero>? ) : ZResponse<List<Hero>?>()
