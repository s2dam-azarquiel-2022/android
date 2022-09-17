package net.azarquiel.guessnumberfrom0to100

import android.os.Bundle
import android.text.Editable
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var randomNumber: Short = 0

    private inline fun <T> toEditable(self: T): Editable {
        return Editable.Factory.getInstance().newEditable(self.toString())
    }

    private fun inc(self: EditText) {
        self.text = toEditable(self.text.toString().toInt() + 1)
    }

    private fun dec(self: EditText) {
        self.text = toEditable(self.text.toString().toInt() - 1)
    }

    private inline fun showS(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private inline fun showL(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun check(self: EditText) {
        val n: Short = self.text.toString().toShort()
        if (n !in 0..100) {
            showS("The number must be between 0 and 100.")
        }

        showL(
            if (n == this.randomNumber) { "Correct number!" }
            else { "Your number is too " + (if (n > this.randomNumber) "big" else "small") + "!" }
        )
    }

    private fun setRandomNumber() {
        this.randomNumber = (0..100).random().toShort()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRandomNumber()

        val numberInput: EditText = findViewById(R.id.numberInput)
        val add1Btn: ImageButton = findViewById(R.id.add1Btn)
        val rem1Btn: ImageButton = findViewById(R.id.rem1Btn)
        val checkButton: Button = findViewById(R.id.checkBtn)

        add1Btn.setOnClickListener { inc(numberInput) }
        rem1Btn.setOnClickListener { dec(numberInput) }
        checkButton.setOnClickListener { check(numberInput) }
    }
}