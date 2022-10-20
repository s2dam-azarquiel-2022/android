package net.azarquiel.pressthemole

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.*
import kotlin.math.absoluteValue
import kotlin.random.nextInt

@SuppressLint("ViewConstructor")
@OptIn(DelicateCoroutinesApi::class)
class Mole(
    context: Context,
    private val burrow: Burrow,
) : androidx.appcompat.widget.AppCompatImageView(context) {
    private var isShiny: Boolean = false
    private var animation: AnimationDrawable
    private var isAlive = true

    init {
        burrow.moles.add(this)

        isShiny = burrow.rnGesus.nextInt(burrow.maxLuckyNumber) == burrow.luckyNumber

        burrow.rnGesus.nextInt(burrow.sizeRatio).let { s ->
            this.layoutParams = ConstraintLayout.LayoutParams(s, s)
        }

        this.moveRandom()

        this.setBackgroundResource(
            if (isShiny) burrow.shinyMoleSkin
            else burrow.moleSkin
        )
        animation = this.background as AnimationDrawable
        animation.start()

        GlobalScope.launch {
            while (isAlive) {
                repeat(burrow.rnGesus.nextInt(Stats.delayCountMin..Stats.delayCountMax)) {
                    if (isAlive) {
                        delay(1000)
                        launch(Dispatchers.Main) {
                            burrow.soundPool.play(burrow.moleShowSound, 1f, 1f, 1, 0, 1f)
                        }
                        delay(burrow.delayDuration - 1000)
                    }
                }
                launch(Dispatchers.Main) {
                    this@Mole.visibility = View.INVISIBLE
                    animation.stop()
                }
                delay(burrow.rnGesus.nextLong(Stats.excavatingDuration.first))
                launch(Dispatchers.Main) {
                    this@Mole.visibility = View.VISIBLE
                    this@Mole.moveRandom()
                    animation.start()
                }
            }
        }

        this.setOnClickListener {
            if (animation.current != animation.getFrame(0)) {
                isAlive = false
                burrow.soundPool.play(burrow.moleKillSound, 1f, 1f, 2, 0, 1f)
                showScorePoints((burrow.width / this.layoutParams.width).let { n ->
                    if (this.isShiny) n * burrow.shinyPointsMultiplier
                    else n
                } / Stats.pointsRatio * burrow.pointsMultiplier)
                burrow.mainLayout.removeView(this)
                burrow.moles.remove(this)
            }
            else showScorePoints(Stats.missclickPointsLost)
        }
    }

    private fun showScorePoints(points: Int) {
        burrow.score += points
        burrow.pointsView.text = context.getString(R.string.points, burrow.score)
        burrow.mainLayout.addView(TextView(context).also {
            if (points < 0) it.setTextColor(Color.RED)
            it.text = context.getString(
                R.string.pointsGained,
                if (points >= 0) '+' else '-',
                points
            )
            it.x = this.x + (this.layoutParams.width / 2)
            it.y = this.y + (this.layoutParams.height / 2)
            GlobalScope.launch {
                launch(Dispatchers.Main) {
                    ObjectAnimator.ofFloat(it, "translationY", it.y - 100).apply {
                        duration = Stats.pointsTextAnimationDuration
                        start()
                    }
                }
                delay(Stats.pointsTextAnimationDuration)
                launch(Dispatchers.Main) { burrow.mainLayout.removeView(it) }
            }
        })
    }

    private fun posIsCorrect(): Boolean {
        for (mole in burrow.moles) {
            if (
                mole != this &&
                (this.x - mole.x).absoluteValue < burrow.sizeRatio.average() &&
                (this.y - mole.y).absoluteValue < burrow.sizeRatio.average()
            ) { return false }
        }
        return true
    }

    private fun moveRandom() {
        do {
            this.x = burrow.rnGesus.nextInt(burrow.width).toFloat()
            this.y = burrow.rnGesus.nextInt(burrow.height).toFloat()
        } while (!posIsCorrect())
    }
}