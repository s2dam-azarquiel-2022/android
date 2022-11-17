package net.azarquiel.translator.controller.handler

import android.speech.tts.TextToSpeech
import android.util.Log

class TTSHandler : TextToSpeech.OnInitListener {
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // ...
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }
}