package com.example.readbook.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.readbook.models.URL.BASE_URL
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.UUID


class ApiClient {
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

    fun getMeMoney(token: Token?): Any? {
        Log.d("GET", "Getting a user money")
        return parser(getRequest(URL("$BASE_URL/me/money"), token!!.accessToken), "GET", User::class.java)
    }

    fun buyBook(bookUuid: UUID, token: Token) {
        Log.d("PUT", "Buying a book")
        putRequest(URL("$BASE_URL/orders/books/$bookUuid"), accessToken = token.accessToken, action = "BuyBook")
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

    fun updateToken(token: Token): Any? {
        Log.d("POST", "Updating the access token")
        return parser(postRequest(URL("$BASE_URL/session/access-token"), refreshToken = token.refreshToken, action = "RefreshToken"), "POST", Token::class.java)
    }

    fun checkBuy(bookUuid: UUID, accessToken: String): Boolean {
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

    fun getGenreBooks(id: Int?): Any? {
        Log.d("GET", "Searching for a book by genre")
        return parser(getRequest(URL("$BASE_URL/books?genre=$id")), "GET", object : TypeToken<List<Book?>?>() {}.type)
    }

    fun changeDataUser(fName: String, sName: String, lName: String, username: String, accessToken: String) {
        Log.d("PUT", "Changing user data")
        putRequest(URL("$BASE_URL/me"), fName, sName, lName, username, accessToken = accessToken, action = "ChangeData")
    }

    fun changePasswordUser(oldPass: String, newPass: String, accessToken: String) {
        Log.d("PUT", "Changing user password")
        putRequest(URL("$BASE_URL/me/password"), oldPass = oldPass, newPass = newPass, accessToken = accessToken, action = "ChangePass")
    }

    fun changeAvatar(bitmap: Bitmap?, accessToken: String, context: Context?) {
        Log.d("PUT", "Changing user avatar")
        putRequest(URL("$BASE_URL/me/avatar"), bitmap = bitmap, accessToken = accessToken, context = context, action = "ChangeAvatar")
    }

    fun getReviews(id: UUID): Any? {
        Log.d("GET", "Searching for a reviews by bookId")
        return parser(getRequest(URL("$BASE_URL/books/$id/reviews")), "GET", object : TypeToken<List<Review?>?>() {}.type)
    }

    fun getReview(bookUuid: UUID, username: String): Any? {
        Log.d("GET", "Checking review the book")
        return parser(getRequest(URL("$BASE_URL/books/$bookUuid/reviews?author=$username")), "GET", object : TypeToken<List<Review?>?>() {}.type)
    }

    fun postReview(id: String, rating: Int, message: String, accessToken: String) {
        Log.d("POST", "Sending a review")
        postRequest(URL("$BASE_URL/books/$id/reviews"), action = "PostReview", rating = rating, message = message, accessToken = accessToken)
    }

    fun getContent(bookUuid: UUID, accessToken: String): ByteArrayOutputStream {
        Log.d("GET", "Getting the contents of the book")
        return getContentRequest(URL("$BASE_URL/books/$bookUuid/content"), accessToken)
    }

    fun checkUser(username: String): Boolean {
        Log.d("HEAD", "Checking user")
        return headRequest(URL("$BASE_URL/users/$username"))
    }

    fun deposit(moneyRub: Int, username: String) {
        Log.d("POST", "Topping up your profile balance")
        postRequest(URL("$BASE_URL/users/$username/money"), moneyRub = moneyRub, action = "Deposit")
    }

    fun getBooksAddedOn(): Any? {
        Log.d("GET", "Getting books by addedOn")
        return parser(getRequest(URL("$BASE_URL/books?sort=addedOn&per_page=10")), text = "GET", type = object : TypeToken<List<Book?>?>() {}.type)
    }

    private fun getContentRequest(url: URL, accessToken: String): ByteArrayOutputStream {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            setRequestProperty("Accept", "application/pdf")
            setRequestProperty("Authorization", "Bearer $accessToken")

            val `is`: InputStream = BufferedInputStream(inputStream)
            val baos = ByteArrayOutputStream()
            val buffer = byteArrayOf(1024.toByte())
            var resultLength = 1
            while (resultLength != -1) {
                resultLength = `is`.read(buffer)
                baos.write(buffer)
            }
            return baos
        }
    }

    suspend fun saveFile(context: Context, bookUuid: UUID, baos: ByteArrayOutputStream?) {
        val folder = context.getExternalFilesDir("books")
        val file = File(folder, "$bookUuid.pdf")
        val fos = withContext(Dispatchers.IO) {
            FileOutputStream(file)
        }
        withContext(Dispatchers.IO) {
            fos.write(baos?.toByteArray())
        }

        withContext(Dispatchers.IO) {
            context.openFileOutput("$bookUuid.pdf", Context.MODE_PRIVATE).use {
                it.write(baos?.toByteArray())
            }
        }
    }

    private fun headRequest(url: URL, accessToken: String = ""): Boolean {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "HEAD"
            connectTimeout = 10000
            if(accessToken != "")
                setRequestProperty("Authorization", "Bearer $accessToken")

            Log.d("HEAD", "$url")
            Log.d("HEAD", "$responseCode $responseMessage")

            if(responseCode == HttpURLConnection.HTTP_OK) {
                return true
            }
            return false
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
        username: String = "",
        rating: Int = 0,
        message: String = "",
        accessToken: String = "",
        moneyRub: Int = 0
    ): BufferedReader {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            doOutput = true
            connectTimeout = 10000
            setRequestProperty("Content-Type", "application/json")
            if(action == "RefreshToken")
                setRequestProperty("Authorization", "Bearer $refreshToken")
            if(action == "PostReview")
                setRequestProperty("Authorization", "Bearer $accessToken")

            val os = outputStream
            val osw = OutputStreamWriter(os, "UTF-8")
            when(action) {
                "ChangePassword" -> {
                    osw.write("{ \"reset_token\": \"$code\", \"password\": \"$password\" }")
                }

                "RegistrationToken" -> {
                    osw.write("{ \"email\": \"$mail\" }")
                }

                "Registration" -> {
                    osw.write("{ \"registration_token\": \"$regToken\", \"username\": \"$username\", \"password\": \"$password\" }")
                }

                "Auth" -> {
                    osw.write("{ \"username\": \"$mail\", \"password\": \"$password\" }")
                }

                "PostReview" -> {
                    osw.write("{ \"score\": \"${rating.toFloat()}\", \"message\": \"${message.replace("\n", "\\n")}\" }")
                }

                "Deposit" -> {
                    osw.write("{ \"money_rub\": $moneyRub }")
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
            connectTimeout = 10000

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
            connectTimeout = 10000

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

    private fun putRequest(
        url: URL,
        fName: String = "",
        sName: String = "",
        lName: String = "",
        username: String = "",
        oldPass: String = "",
        newPass: String = "",
        bitmap: Bitmap? = null,
        context: Context? = null,
        accessToken: String,
        action: String
    ) {
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "PUT"
            doOutput = true
            doInput = true
            connectTimeout = 10000
            val bitmapdata: ByteArray?
            var file: File? = null

            setRequestProperty("Accept", "application/json")
            if(action == "ChangeAvatar") {
                file = File(context!!.cacheDir, "image")
                val bos = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 0, bos)
                bitmapdata = bos.toByteArray()

                val fos = FileOutputStream(file)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()

                setRequestProperty("Content-Type", "image/bmp")
                setRequestProperty("Content-Length", "${bitmapdata.size}")
            }
            else {
                setRequestProperty("Content-Type", "application/json")
            }
            setRequestProperty("Authorization", "Bearer $accessToken")

            val os = outputStream
            val osw = OutputStreamWriter(os, "UTF-8")
            when(action) {
                "ChangeData" -> {
                    osw.write("{ \"username\": \"$username\", \"first_name\": \"$fName\", \"second_name\": \"$sName\", \"last_name\": \"$lName\" }")
                }
                "ChangePass" -> {
                    osw.write("{ \"old_password\": \"$oldPass\", \"new_password\": \"$newPass\" }")
                }
                "ChangeAvatar" -> {
                    val fis = FileInputStream(file)
                    var bytesRead: Int
                    val dataBuffer = ByteArray(1024)
                    while (fis.read(dataBuffer).also { bytesRead = it } != -1) {
                        os.write(dataBuffer, 0, bytesRead)
                    }
                }
            }
            osw.flush()
            osw.close()
            os.close()

            Log.d("PUT", "$url")
            Log.d("PUT", "$responseCode $responseMessage")
        }
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