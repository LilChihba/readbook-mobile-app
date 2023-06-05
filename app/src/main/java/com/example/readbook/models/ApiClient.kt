package com.example.readbook.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
    private val baseUrl: String = "https://f466-85-95-178-202.ngrok-free.app"

    @RequiresApi(Build.VERSION_CODES.O)
    fun auth(mail: String, password: String): Any? {
        Log.d("POST", "---AUTH---")
        return parser(postRequest(URL("$baseUrl/session"), mail, password), "POST", Token::class.java)
    }

    fun getMe(token: Token): Any? {
        Log.d("GET", "---ME---")
        return parser(getRequest(URL("$baseUrl/me"), token.accessToken), "GET", User::class.java)
    }

    fun getMeAvatar(username: String): Bitmap? {
        Log.d("GET", "---ME/AVATAR---")
        return parserImage(URL("$baseUrl/users/$username/avatar").openConnection() as HttpURLConnection)
    }

    fun getMeEmail(username: String, token: Token): User? {
        Log.d("GET", "---ME/EMAIL---")
        var output: String?
        while (getRequest(URL("$baseUrl/me/email"), token.accessToken).readLine().also { output = it } != null) {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
            return gson.fromJson(output, User::class.java)
        }
        return null
    }

    fun getBooks(): Any? {
        Log.d("GET", "---BOOKS---")
        return parser(getRequest(URL("$baseUrl/books")), "GET", object : TypeToken<List<Book?>?>() {}.type)
    }

    fun getCoverBook(uid: UUID): Bitmap? {
        Log.d("GET", "---COVERBOOK---")
        return parserImage(URL("$baseUrl/books/$uid/cover").openConnection() as HttpURLConnection)
    }

    fun getLibraryBook() {
        return
    }

    fun getLibraryBooks() {
        return
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateToken(token: Token): Any? {
        Log.d("POST", "---UPDATETOKEN---")
        return parser(postRequest(URL("$baseUrl/session/access-token"), refreshToken = token.refreshToken), "POST", Token::class.java)
    }

    private fun postRequest(url: URL, mail: String = "", password: String = "", refreshToken: String = ""): BufferedReader {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            if(refreshToken != "")
                setRequestProperty("Authorization", "Bearer $refreshToken")

            if(mail != "" && password != "") {
                val os = outputStream
                val osw = OutputStreamWriter(os, "UTF-8")
                osw.write("{ \"username\": \"$mail\", \"password\": \"$password\"}")
                osw.flush()
                osw.close()
                os.close()
            }

            Log.d("POST", "$url")
            Log.d("POST", "$responseCode $responseMessage")

            return BufferedReader(InputStreamReader(inputStream))
        }
    }

    private fun getRequest(url: URL, accessToken: String = ""): BufferedReader {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            if(accessToken != "")
                setRequestProperty("Authorization", "Bearer $accessToken")

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

    private fun parserImage(urlConnection: HttpURLConnection): Bitmap? {
        return try {
            val `is` = BufferedInputStream(urlConnection.inputStream)
            BitmapFactory.decodeStream(`is`)
        } catch (e: IOException) {
            return null
        } finally {
            urlConnection.disconnect()
        }
    }
}