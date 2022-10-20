package net.azarquiel.dice

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import kotlin.random.Random
import kotlin.random.nextInt

class Dice(
    c: Context,
    a: AttributeSet,
    d: Int
) : androidx.appcompat.widget.AppCompatImageView(c, a, d), OnClickListener {
    constructor(c: Context, a: AttributeSet) : this(c, a, 0)

    private fun setBackgroundResource(name: String) {
        this.setBackgroundResource(resources.getIdentifier(name, "drawable", context.packageName))
    }

    var rnGesus: Random? = null
    private val animation: AnimationDrawable =
        context.theme.obtainStyledAttributes(a, R.styleable.Dice, 0, 0).let {
            val animation = ContextCompat.getDrawable(
                context,
                it.getResourceId(R.styleable.Dice_rollAnimation, R.drawable.dice1_animation)
            ) as AnimationDrawable
            it.recycle()
            animation
        }
    var face: Face = Face.face0
    private var isPinned: Boolean = false

    init {
        this.setOnClickListener(this)
        this.isEnabled = false
    }

    fun clear() {
        this.isEnabled = false
        this.isPinned = false
        this.setBackgroundResource("face_unknown")
    }

    fun roll() {
        this.isEnabled = false
        if (!isPinned) {
            this.background = animation
            animation.start()
        }
    }

    fun stop() {
        this.isEnabled = true
        if (!isPinned) {
            animation.stop()
            face = Face.values()[rnGesus!!.nextInt(1..5)]
            this.setBackgroundResource("$face")
        }
    }

    override fun onClick(p0: View?) {
        isPinned = !isPinned
        this.setBackgroundResource(if (isPinned) "${face}_pinned" else "$face")
    }
}