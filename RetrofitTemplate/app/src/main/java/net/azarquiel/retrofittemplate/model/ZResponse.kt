package net.azarquiel.retrofittemplate.model

import com.google.gson.annotations.SerializedName

abstract class ZResponse<T> { abstract val data: T }

data class User ( @SerializedName("usuario") override val data: UserData? ) : ZResponse<UserData?>()
