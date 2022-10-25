package net.azarquiel.tmdb.dao

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import java.net.URL

object TMDB {
    private const val api = "https://api.themoviedb.org"
    private const val path = "3/person/popular"
    private const val apiKey = "api_key=b8a40ad963d530b1a6997d1be5fa52cf"
    private const val url = "$api/$path?$apiKey"

    data class Result(
        @SerializedName("results")
        var data: List<Person>
    )

    data class Person(
        var name: String,
        var popularity: Float,
        @SerializedName("profile_path")
        private var pfp: String?,
        var id: Int
    ) {
        fun getPfp(): String? = if (pfp == null) null else "https://image.tmdb.org/t/p/w500$pfp"
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun getPeople(): Result {
        var result: Result? = null
        GlobalScope.launch {
            val json = URL(url).readText(Charsets.UTF_8)
            result = Gson().fromJson(json, Result::class.java)
        }.join()
        return result!!
    }
}