package com.example.readbook.models

import android.util.Base64
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.UUID

class ApiClient {
    private val baseAuthUrl: String = "https://93c3-85-95-178-202.ngrok-free.app/oauth2/token"
    private val baseResourceUrl: String = "https://92ae-85-95-178-202.ngrok-free.app/"
    var accessToken: String = ""
    var expiresIn: Int = 0

    fun auth(mail: String, password: String) {
        val url: URL = URL(baseAuthUrl)
        val requestParams: String = URLEncoder.encode("grant_type", "UTF-8") + "=" + URLEncoder.encode("password", "UTF-8" ) + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
        val userCredentials: String = "test-client:test-client"
        val basicAuth: String = "Basic " + String(Base64.encode(userCredentials.toByteArray(), 0))

        postRequest(url, requestParams, basicAuth)
    }

    fun getBook(bookId: UUID) {
        val url: String = "$baseResourceUrl/books/$bookId"
    }

    private fun postRequest(url: URL, requestParams: String, basicAuth: String) {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            setRequestProperty("Authorization", basicAuth)

            val owr = OutputStreamWriter(outputStream)
                owr.write(requestParams)
                owr.flush()
                owr.close()

            Log.d("POST", "$url")
            Log.d("POST", "Authorization: $basicAuth")
            Log.d("POST", "$responseCode $responseMessage")

            val br = BufferedReader(InputStreamReader(inputStream))
            parser(br)
        }
    }

    private fun getRequest() {
        return
    }

    private fun deleteRequest() {
        return
    }

    private fun putRequest() {
        return
    }

    private fun parser(br: BufferedReader) {
        var output: String?
        while (br.readLine().also { output = it } != null) {
            Log.d("POST", "$output")
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
            gson.fromJson(output, ApiClient::class.java)
        }
    }
}