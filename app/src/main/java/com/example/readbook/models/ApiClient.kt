package com.example.readbook.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID


class ApiClient {
    private val baseUrl: String = "https://1b83-85-95-178-202.ngrok-free.app"

    fun auth(mail: String, password: String) {
        val url = URL("$baseUrl/session")
        val br = postRequest(url, mail, password)
        parser(br, "POST", Token::class.java)
    }

    fun getBook(bookId: UUID) {
        val url = "$baseUrl/books/$bookId"
    }

    fun getBooks(): Any? {
        val url = URL("$baseUrl/books")
        val type = object : TypeToken<List<Book?>?>() {}.type
        val br = getRequest(url)
        return parser(br, "GET", type)
    }

    fun getCoverBook(uid: UUID): Bitmap? {
        val url = URL("$baseUrl/books/$uid/cover")
        val urlConnection = url.openConnection() as HttpURLConnection

        return try {
            val `is` = BufferedInputStream(urlConnection.inputStream)
            BitmapFactory.decodeStream(`is`)
        } catch (e: IOException) {
            return null
        } finally {
            urlConnection.disconnect()
        }
    }

    fun getLibraryBook() {
        return
    }

    fun getLibraryBooks() {
        return
    }

    private fun postRequest(url: URL, mail: String, password: String): BufferedReader {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            setRequestProperty("Content-Type", "application/json")

            val os = outputStream
            val osw = OutputStreamWriter(os, "UTF-8")
            osw.write("{ \"username\": \"$mail\", \"password\": \"$password\"}")
            osw.flush()
            osw.close()
            os.close()

            Log.d("POST", "$url")
            Log.d("POST", "$responseCode $responseMessage")

            return BufferedReader(InputStreamReader(inputStream))
        }
    }

    private fun getRequest(url: URL): BufferedReader {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            Log.d("GET", "$url")
            Log.d("GET", "$responseCode $responseMessage")
            return BufferedReader(InputStreamReader(inputStream))
        }
    }

    private fun deleteRequest() {
        return
    }

    private fun putRequest() {
        return
    }

    private fun parser(br: BufferedReader, text: String = "POST", type: Type): Any? {
        var output: String?
        while (br.readLine().also { output = it } != null) {
            Log.d(text, "$output")
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
            return gson.fromJson(output, type)
        }
        return null
    }
}