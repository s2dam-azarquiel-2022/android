package net.azarquiel.pressthemole

import android.media.SoundPool
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
    @JvmField var score: Long = Stats.scoreDefault,
    @JvmField var pointsMultiplier: Int = Stats.pointsMultiplierDefault,
    @JvmField var shinyPointsMultiplier: Int = Stats.shinyPointsMultiplierDefault,
    @JvmField var delayDuration: Long = Stats.delayDurationDefault,
    @JvmField var moleSkin: Int = Stats.moleSkinDefault,
    @JvmField var shinyMoleSkin: Int = Stats.shinyMoleSkinDefault,
    @JvmField var maxLuckyNumber: Int = Stats.maxLuckyNumber,
    @JvmField var luckyNumber: Int = rnGesus.nextInt(maxLuckyNumber),
    @JvmField val soundPool: SoundPool,
    @JvmField var moleShowSound: Int,
    @JvmField var moleKillSound: Int,
)
