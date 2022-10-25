package net.azarquiel.darksky.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import net.azarquiel.darksky.R

class TextFA(
    c: Context,
    a: AttributeSet,
    d: Int,
) : androidx.appcompat.widget.LinearLayoutCompat(c, a, d) {
    constructor(c: Context, a: AttributeSet) : this(c, a, 0)

    init {
        inflate(c, R.layout.text_fa, this)
        context.theme.obtainStyledAttributes(a, R.styleable.TextFA, 0, 0).let { t ->
            findViewById<TextView>(R.id.text).let {
                it.text = t.getString(R.styleable.TextFA_text)
                t.getResourceId(R.styleable.TextFA_textId, 0).let { id -> if (id != 0) it.id = id }
            }
            findViewById<TextView>(R.id.icon).text = t.getString(R.styleable.TextFA_faIcon)
            t.recycle()
        }
    }
}