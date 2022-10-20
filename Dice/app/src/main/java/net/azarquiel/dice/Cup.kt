package net.azarquiel.dice

import android.widget.LinearLayout
import kotlin.random.Random

fun genDefaultPlayers(): Array<Player> { return Array(2) { i -> Player("Player$i") } }

data class Cup(
    @JvmField var diceHolder: LinearLayout,
    @JvmField var rnGesus: Random,
    @JvmField var players: Array<Player> = genDefaultPlayers(),
    @JvmField var currentPlayer: Int = 0,
    @JvmField var dice: Array<Dice> = Array(diceHolder.childCount) { i ->
        (diceHolder.getChildAt(i) as Dice).also { it.rnGesus = rnGesus }
    }
) {
    // Just so that the warning to create them doesn't show
    override fun equals(other: Any?): Boolean { TODO() }
    override fun hashCode(): Int { TODO() }
}