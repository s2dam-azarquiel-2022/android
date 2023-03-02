package net.azarquiel.chistes.model

import com.google.gson.annotations.SerializedName

abstract class ZResponse<T> { abstract val data: T }

data class UserResponse (@SerializedName("usuario") override val data: User? ) : ZResponse<User?>()
data class CategoriesResponse (@SerializedName("categorias") override val data: List<Category> ) : ZResponse<List<Category>>()
data class JokesResponse (@SerializedName("chistes") override val data: MutableList<Joke> ) : ZResponse<MutableList<Joke>>()
data class JokeResponse (@SerializedName("chiste") override val data: Joke? ) : ZResponse<Joke?>()
data class AvgResponse (@SerializedName("avg") override val data: Int ) : ZResponse<Int>()
data class RatingResponse (@SerializedName("punto") override val data: Rating? ) : ZResponse<Rating?>()
