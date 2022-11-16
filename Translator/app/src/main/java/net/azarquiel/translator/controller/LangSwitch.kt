package net.azarquiel.translator.controller

class LangSwitch(
    var currentLangFrom: String,
    var currentLangTo: String,
) {
    val langs = arrayOf("es", "en")
    var currentLangFromPos = langs.indexOf(currentLangFrom)
    var currentLangToPos = langs.indexOf(currentLangTo)

    fun nextLangFrom() {
        currentLangFromPos++
        if (currentLangFromPos == langs.size) currentLangFromPos = 0
        currentLangFrom = langs[currentLangFromPos]
    }

    fun nextLangTo() {
        currentLangToPos++
        if (currentLangToPos == langs.size) currentLangToPos = 0
        currentLangTo = langs[currentLangToPos]
    }
}