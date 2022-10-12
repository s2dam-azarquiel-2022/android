package net.azarquiel.pressthemole

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class Mole(
    context: Context,
    sizeRatio: IntRange,
    mainW: Int,
    mainH: Int,
    luckyNumber: Int,
    fOk: (self: Mole) -> Unit,
    fWrong: (self: Mole) -> Unit
) : androidx.appcompat.widget.AppCompatImageView(context) {
    var isShiny: Boolean = false
    private var animation: AnimationDrawable
    private val rnGesus: Random = Random(System.currentTimeMillis())

    init {
        isShiny = rnGesus.nextInt(100) == luckyNumber

        rnGesus.nextInt(sizeRatio).let { s ->
            this.layoutParams = ConstraintLayout.LayoutParams(s, s)
        }

        this.moveRandom(mainW, mainH)

        this.setBackgroundResource(
            if (isShiny) R.drawable.special_animated
            else R.drawable.normal_animated
        )
        animation = this.background as AnimationDrawable
        animation.start()

        GlobalScope.launch {
            while (true) {
                delay(rnGesus.nextInt(1..3).toLong() * 1600)
                launch(Dispatchers.Main) { this@Mole.visibility = View.INVISIBLE }
                delay(500)
                launch(Dispatchers.Main) {
                    this@Mole.visibility = View.VISIBLE
                    this@Mole.moveRandom(mainW, mainH)
                }
            }
        }

        this.setOnClickListener {
            if (animation.current != animation.getFrame(0)) fOk(this)
            else fWrong(this)
        }
    }

    private fun moveRandom(mainW: Int, mainH: Int) {
        this.x = rnGesus.nextInt(mainW).toFloat()
        this.y = rnGesus.nextInt(mainH).toFloat()
    }
}