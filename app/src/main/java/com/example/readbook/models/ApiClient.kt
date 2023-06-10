package com.example.readbook.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.readbook.models.URL.BASE_URL
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
import java.net.URLEncoder
import java.util.UUID


class ApiClient {
    @RequiresApi(Build.VERSION_CODES.O)
    fun auth(mail: String, password: String): Any? {
        Log.d("POST", "Getting the authorization token")
        return parser(postRequest(URL("$BASE_URL/session"), mail = mail, password = password, action = "Auth"), "POST", Token::class.java)
    }

    fun registration(regToken: String, username: String, password: String) {
        Log.d("POST", "Registration...")
        val url = URL("$BASE_URL/account")
        postRequest(url, regToken = regToken, username = username, password = password, action = "Registration")
    }

    fun getMe(token: Token): Any? {
        Log.d("GET", "Getting user data")
        return parser(getRequest(URL("$BASE_URL/me"), token.accessToken), "GET", User::class.java)
    }

    fun getMeAvatar(username: String): Bitmap? {
        Log.d("GET", "Getting a user avatar")
        return parserImage(URL("$BASE_URL/users/$username/avatar").openConnection() as HttpURLConnection)
    }

    fun getMeEmail(token: Token): User? {
        Log.d("GET", "Getting a user mail")
        var output: String?
        while (getRequest(URL("$BASE_URL/me/email"), token.accessToken).readLine().also { output = it } != null) {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
            return gson.fromJson(output, User::class.java)
        }
        return null
    }

    fun getBooks(): Any? {
        Log.d("GET", "Getting a list of books")
        return parser(getRequest(URL("$BASE_URL/books")), "GET", object : TypeToken<List<Book?>?>() {}.type)
    }

    fun getLibraryBooks(token: Token): Any? {
        Log.d("GET", "Getting a list of books from the library")
        return parser(getRequest(URL("$BASE_URL/library/books"), token.accessToken), "GET", object : TypeToken<List<Book?>?>() {}.type)
    }

    fun getRegToken(mail: String) {
        Log.d("POST", "Gettings the registration token")
        postRequest(URL("$BASE_URL/registration-token"), mail = mail, action = "RegistrationToken")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateToken(token: Token): Any? {
        Log.d("POST", "Updating the access token")
        return parser(postRequest(URL("$BASE_URL/session/access-token"), refreshToken = token.refreshToken, action = "RefreshToken"), "POST", Token::class.java)
    }

    fun checkBuy(bookUuid: UUID, accessToken: String): Int {
        Log.d("HEAD", "Checking whether the book has been purchased")
        return headRequest(URL("$BASE_URL/library/books/$bookUuid"), accessToken)
    }

    fun getCodeInEmail(mail: String, username: String): Int {
        Log.d("DELETE", "Getting the code by email for restore password")
        return deleteRequest(URL("$BASE_URL/users/$username/password"), mail)
    }

    fun changePassword(code: String, password: String, username: String) {
        Log.d("POST", "Changing the password")
        postRequest(URL("$BASE_URL/users/$username/password"), password = password, code = code, action = "ChangePassword")
    }

    fun searchBooks(text: String): Any? {
        Log.d("GET", "Searching the book")
        return parser(getRequest(URL("$BASE_URL/books?title=${URLEncoder.encode(text, "utf-8")}")), "GET", object : TypeToken<List<Book?>?>() {}.type)
    }

    private fun headRequest(url: URL, accessToken: String): Int {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "HEAD"
            setRequestProperty("Authorization", "Bearer $accessToken")

            Log.d("HEAD", "$url")
            Log.d("HEAD", "$responseCode $responseMessage")

            return responseCode
        }
    }

    private fun postRequest(
        url: URL,
        action: String = "",
        mail: String = "",
        code: String = "",
        password: String = "",
        refreshToken: String = "",
        regToken: String = "",
        username: String = ""
    ): BufferedReader {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            setRequestProperty("Content-Type", "application/json")

            val os = outputStream
            val osw = OutputStreamWriter(os, "UTF-8")
            when(action) {
                "RefreshToken" -> {
                    setRequestProperty("Authorization", "Bearer $refreshToken")
                }

                "ChangePassword" -> {
                    osw.write("{ \"reset_token\": \"$code\", \"password\": \"$password\" }")
                }

                "RegistrationToken" -> {
                    osw.write("{ \"email\": \"$mail\"}")
                }

                "Registration" -> {
                    osw.write("{ \"registration_token\": \"$regToken\", \"username\": \"$username\", \"password\": \"$password\"}")
                }

                "Auth" -> {
                    osw.write("{ \"username\": \"$mail\", \"password\": \"$password\"}")
                }
            }
            osw.flush()
            osw.close()
            os.close()

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

    private fun deleteRequest(url: URL, mail: String): Int {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "DELETE"
            doOutput = true

            setRequestProperty("Content-Type", "application/json")

            val os = outputStream
            val osw = OutputStreamWriter(os, "UTF-8")
            osw.write("{ \"email\": \"$mail\"}")
            osw.flush()
            osw.close()
            os.close()

            Log.d("DELETE", "$url")
            Log.d("DELETE", "$responseCode $responseMessage")

            return responseCode
        }
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