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

    init {
        burrow.moles.add(this)

        isShiny = burrow.rnGesus.nextInt(maxLuckyNumber) == burrow.luckyNumber

        burrow.rnGesus.nextInt(burrow.sizeRatio).let { s ->
            this.layoutParams = ConstraintLayout.LayoutParams(s, s)
        }

        this.moveRandom()

        this.setBackgroundResource(
            if (isShiny) R.drawable.special_animated
            else R.drawable.normal_animated
        )
        animation = this.background as AnimationDrawable
        animation.start()

        GlobalScope.launch {
            while (true) {
                delay(burrow.rnGesus.nextInt(delayCountMin..delayCountMax).toLong() * delayDuration)
                launch(Dispatchers.Main) { this@Mole.visibility = View.INVISIBLE }
                delay(excavatingDuration)
                launch(Dispatchers.Main) {
                    this@Mole.visibility = View.VISIBLE
                    this@Mole.moveRandom()
                }
            }
        }

        this.setOnClickListener {
            if (animation.current != animation.getFrame(0)) {
                showScorePoints((burrow.width / this.layoutParams.width).let { n ->
                    if (this.isShiny) n * burrow.luckyNumber
                    else n / pointsRatio
                })
                burrow.mainLayout.removeView(this)
                burrow.moles.remove(this)
            }
            else showScorePoints(missclickPointsLost)
        }
    }

    private fun showScorePoints(score: Int) {
        burrow.score += score
        burrow.pointsView.text = context.getString(R.string.points, burrow.score)
        burrow.mainLayout.addView(TextView(context).also {
            if (score < 0) it.setTextColor(Color.RED)
            it.text = context.getString(
                R.string.pointsGained,
                if (score >= 0) '+' else '-',
                score
            )
            it.x = this.x + (this.layoutParams.width / 2)
            it.y = this.y + (this.layoutParams.height / 2)
            GlobalScope.launch {
                launch(Dispatchers.Main) {
                    ObjectAnimator.ofFloat(it, "translationY", -100F).apply {
                        duration = scoreTextAnimationDuration
                        start()
                    }
                }
                delay(scoreTextAnimationDuration)
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