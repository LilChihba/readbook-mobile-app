package com.example.readbook

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.readbook.navigation.MainScreen
import com.example.readbook.ui.theme.ReadbookTheme
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory

@RequiresApi(Build.VERSION_CODES.P)
class MainActivity : ComponentActivity() {
    var pref : SharedPreferences? = null

    private var billingClient: RuStoreBillingClient? = null

//    private val billingClient: RuStoreBillingClient = RuStoreBillingClientFactory.create(
//        context = context,
//        consoleApplicationId = "2063485409",
//        deeplinkScheme = "readbookscheme"
//    )
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Readbook)
        super.onCreate(savedInstanceState)

        val context: Context = applicationContext

        billingClient = RuStoreBillingClientFactory.create(
            context = context,
            consoleApplicationId = "2063485409",
            deeplinkScheme = "readbookscheme"
        )

        if (savedInstanceState == null) {
            billingClient?.onNewIntent(intent)
        }

        setContent {
            ReadbookTheme {
                pref = getSharedPreferences("User", Context.MODE_PRIVATE)
//                RuStoreBillingClient.checkPurchasesAvailability(context)
//                    .addOnSuccessListener { result ->
//                        when (result) {
//                            FeatureAvailabilityResult.Available -> {
//                                Log.v("checkPurchasesAvailability", "True")
//                            }
//
//                            is FeatureAvailabilityResult.Unavailable -> {
//                                Log.v("checkPurchasesAvailability", "False")
//                            }
//                        }
//                    }.addOnFailureListener { throwable ->
//                        // Process unknown error
//                    }
                MainScreen(pref)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        billingClient?.onNewIntent(intent)
    }
}
