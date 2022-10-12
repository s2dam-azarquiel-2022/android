package net.azarquiel.pressthemole

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

data class Burrow(
    @JvmField var mainLayout: ConstraintLayout,
    @JvmField var pointsView: TextView,
    @JvmField var rnGesus: Random = Random(System.currentTimeMillis()),
    @JvmField var luckyNumber: Int = rnGesus.nextInt(100),
    @JvmField var sizeRatio: IntRange = 0..0,
    @JvmField var width: Int = 0,
    @JvmField var height: Int = 0,
    @JvmField var maxMoles: Int = 10,
    @JvmField var moles: Int = 0,
    @JvmField var score: Long = 0,
)
