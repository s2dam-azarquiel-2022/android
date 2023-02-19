package net.azarquiel.retrofittemplate.model

import com.google.gson.annotations.SerializedName

data class UserData (
    @SerializedName("id") @JvmField val id: String,
    @SerializedName("nick") @JvmField val nick: String,
    @SerializedName("pass") @JvmField val pass: String,
)
