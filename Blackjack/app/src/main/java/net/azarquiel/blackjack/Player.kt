package net.azarquiel.blackjack

data class Player(
    var points: Int = 0,
    var name: String
) {
    override fun toString(): String {
        return "$name has: $points points"
    }
}