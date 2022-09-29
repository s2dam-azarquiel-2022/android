package net.azarquiel.blackjack

fun List<Player>?.toString(): String {
    if (this == null) return "No one"
    var result = ""
    for (player in this) {
        result += "${player.name}, "
    }
    return (if (result.isNotEmpty()) result.substring(0..result.length-3) else result)
}
