package net.azarquiel.pressthemole

data class Burrow(
    @JvmField var luckyNumber: Int,
    @JvmField var sizeRatio: IntRange = 0..0,
    @JvmField var width: Int = 0,
    @JvmField var height: Int = 0,
    @JvmField var maxMoles: Int = 10,
    @JvmField var moles: Int = 0,
)
