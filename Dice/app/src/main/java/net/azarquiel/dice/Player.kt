package net.azarquiel.dice

data class Player(
    @JvmField var name: String,
    @JvmField var rollsLeft: Int = 3,
    @JvmField var points: Int = 0
)
