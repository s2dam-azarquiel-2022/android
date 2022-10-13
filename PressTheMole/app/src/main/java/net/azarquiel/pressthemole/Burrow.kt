package net.azarquiel.pressthemole

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

data class Burrow(
    @JvmField var mainLayout: ConstraintLayout,
    @JvmField var pointsView: TextView,
    @JvmField var rnGesus: Random = Random(System.currentTimeMillis()),
    @JvmField var sizeRatio: IntRange = 0..0,
    @JvmField var width: Int = 0,
    @JvmField var height: Int = 0,
    @JvmField var moles: MutableList<Mole> = mutableListOf(),
    @JvmField var score: Long = 0,
    @JvmField var pointsMultiplier: Int = 1,
    @JvmField var delayDuration: Long = 1600,
    @JvmField var moleSkin: Int = R.drawable.normal_animated,
    @JvmField var shinyMoleSkin: Int = R.drawable.shiny_animated,
    @JvmField var maxLuckyNumber: Int = 100,
    @JvmField var luckyNumber: Int = rnGesus.nextInt(maxLuckyNumber),
)
