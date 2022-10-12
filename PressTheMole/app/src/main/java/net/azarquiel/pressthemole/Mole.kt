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
        burrow.moles++

        isShiny = burrow.rnGesus.nextInt(100) == burrow.luckyNumber

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
                delay(burrow.rnGesus.nextInt(1..3).toLong() * 1600)
                launch(Dispatchers.Main) { this@Mole.visibility = View.INVISIBLE }
                delay(500)
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
                    else n / 4
                })
                burrow.mainLayout.removeView(this)
                burrow.moles--
            }
            else showScorePoints(-1)
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
                        duration = 500
                        start()
                    }
                }
                delay(500)
                launch(Dispatchers.Main) { burrow.mainLayout.removeView(it) }
            }
        })
    }

    private fun moveRandom() {
        this.x = burrow.rnGesus.nextInt(burrow.width).toFloat()
        this.y = burrow.rnGesus.nextInt(burrow.height).toFloat()
    }
}