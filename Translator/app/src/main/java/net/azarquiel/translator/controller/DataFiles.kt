package net.azarquiel.translator.controller

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.*

object DataFiles {
    @SuppressLint("SdCardPath")
    fun inject(context: Context, xmlFile: String) {
        val sharedPrefsPath = "/data/data/${context.packageName}/shared_prefs"
        val xmlFilePath = "$sharedPrefsPath/$xmlFile"
        if (!File(xmlFilePath).exists()) {
            Toast.makeText(context, "Copying files ...", Toast.LENGTH_LONG).show()
            File(sharedPrefsPath).mkdir()
            try {
                val input: InputStream = context.assets.open(xmlFile)
                val output: OutputStream = FileOutputStream(xmlFilePath)
                val buffer = ByteArray(1024)
                var read = input.read(buffer)
                while (read != -1) {
                    output.write(buffer, 0, read)
                    read = input.read(buffer)
                }
                input.close()
                output.close()
                Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Toast.makeText(context, "There was an error copying the files", Toast.LENGTH_LONG).show()
                Log.e("Translator", "There was an error copying the files", e)
            }
        }
    }
}