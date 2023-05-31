package com.example.readbook

import com.example.readbook.models.ApiClient
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class JsonUnitTest {

    private val tokenJsonString = """
            {
                "access_token" : "Ec3I1e1RWxKvzkx_OJL-dKbzV4c3JXZkqElnOUdvtV7E6ZeZr7DSZma_9Th3_y6Ve_P7Q7cCdN6l9ZA2rDT5NY_5klMshbfd1OnH8hpMf2VLPTXwupWzCkrIaGFHhkAB",
                "token_type" : "Bearer",
                "expires_in" : 1799
            }
        """

    @Test
    fun gsonTest_isCorrect() {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        val token = gson.fromJson(tokenJsonString, ApiClient::class.java)

        assertEquals("Ec3I1e1RWxKvzkx_OJL-dKbzV4c3JXZkqElnOUdvtV7E6ZeZr7DSZma_9Th3_y6Ve_P7Q7cCdN6l9ZA2rDT5NY_5klMshbfd1OnH8hpMf2VLPTXwupWzCkrIaGFHhkAB", token.accessToken)
        assertEquals(1799, token.expiresIn)
    }
}