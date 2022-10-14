package net.azarquiel.pressthemole

object Stats {
    const val minSizeRatio: Int = 8
    const val maxSizeRatio: Int = 14
    const val pointsRatio: Int = 5

    const val delayCountMin: Int = 2
    const val delayCountMax: Int = 5

    const val delayDurationDefault: Long = 1600
    const val delayDurationSlow: Long = delayDurationDefault + 500
    const val excavatingDuration: Long = 1000
    const val newMoleWaitDuration: Long = 2000
    const val pointsTextAnimationDuration: Long = 500

    const val maxMoles: Int = 10
    const val maxLuckyNumber: Int = 100
    const val maxLuckyNumberUpgraded: Int = maxLuckyNumber / 2

    const val pointsMultiplierDefault: Int = 1
    const val pointsMultiplierDouble: Int = pointsMultiplierDefault * 2
    const val shinyPointsMultiplierDefault: Int = 50
    const val shinyPointsMultiplierDouble: Int = shinyPointsMultiplierDefault * 2
    const val missclickPointsLost: Int = -1
    const val scoreDefault: Long = 0

    const val slowerMolesPrice: Int = 100
    const val doublePointsPrice: Int = 200
    const val aLuckierPersonPrice: Int = 500

    const val moleSkinDefault: Int = R.drawable.normal_animated
    const val shinyMoleSkinDefault: Int = R.drawable.shiny_animated
    const val moleSkinSlow = R.drawable.normal_animated_slow
    const val shinyMoleSkinSlow = R.drawable.shiny_animated_slow
}