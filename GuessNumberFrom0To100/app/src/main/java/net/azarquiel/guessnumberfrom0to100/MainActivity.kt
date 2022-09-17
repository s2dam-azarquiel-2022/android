package net.azarquiel.guessnumberfrom0to100

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var randomNumber: Short = 0

    private fun genRandomNumber() {
        this.randomNumber = (0..100).random().toShort()
    }

    private fun EditText.check() {
        val n: Short = this.toShort()
        if (n !in 0..100) { showS("The number must be between 0 and 100.") }
        else { showL(
            if (n == randomNumber) { "Correct number!" }
            else { "Your number is too " + (if (n > randomNumber) "big" else "small") + "!" }
        ) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        genRandomNumber()
        val numberInput: EditText = findViewById(R.id.numberInput)
        findViewById<ImageButton>(R.id.add1Btn).setOnClickListener { numberInput.inc() }
        findViewById<ImageButton>(R.id.rem1Btn).setOnClickListener { numberInput.dec() }
        findViewById<Button>(R.id.checkBtn).setOnClickListener { numberInput.check() }
    }
}