package net.azarquiel.guessnumberfrom0to100

import android.content.Context
import android.text.Editable
import android.widget.EditText
import android.widget.Toast

inline fun <T> toEditable(self: T): Editable {
    return Editable.Factory.getInstance().newEditable(self.toString())
}

inline fun EditText.toInt(): Int {
    return this.text.toString().toInt()
}

inline fun EditText.toShort(): Short {
    return this.text.toString().toShort()
}

fun EditText.inc() {
    this.text = toEditable(this.toInt() + 1)
}

fun EditText.dec() {
    this.text = toEditable(this.toInt() - 1)
}

inline fun Context.showS(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

inline fun Context.showL(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}
