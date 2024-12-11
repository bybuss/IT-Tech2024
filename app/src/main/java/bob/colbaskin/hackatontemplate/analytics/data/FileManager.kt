package bob.colbaskin.hackatontemplate.analytics.data

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class FileManager(private val context: Context) {

    fun saveJsonToFile(fileName: String, json: String) {
        try {
            val file = File(context.filesDir, fileName)
            FileOutputStream(file).use { outputStream ->
                outputStream.write(json.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readJsonFromFile(fileName: String): String? {
        return try {
            val file = File(context.filesDir, fileName)
            FileInputStream(file).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}