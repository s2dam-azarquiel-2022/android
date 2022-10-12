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
    private val burrow: Burrow,
    fOk: (self: Mole) -> Unit,
    fWrong: (self: Mole) -> Unit
) : androidx.appcompat.widget.AppCompatImageView(context) {
    var isShiny: Boolean = false
    private var animation: AnimationDrawable
    private val rnGesus: Random = Random(System.currentTimeMillis())

    init {
        isShiny = rnGesus.nextInt(100) == burrow.luckyNumber

        rnGesus.nextInt(burrow.sizeRatio).let { s ->
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
                delay(rnGesus.nextInt(1..3).toLong() * 1600)
                launch(Dispatchers.Main) { this@Mole.visibility = View.INVISIBLE }
                delay(500)
                launch(Dispatchers.Main) {
                    this@Mole.visibility = View.VISIBLE
                    this@Mole.moveRandom()
                }
            }
        }

        this.setOnClickListener {
            if (animation.current != animation.getFrame(0)) fOk(this)
            else fWrong(this)
        }
    }

    private fun moveRandom() {
        this.x = rnGesus.nextInt(burrow.width).toFloat()
        this.y = rnGesus.nextInt(burrow.height).toFloat()
    }
}