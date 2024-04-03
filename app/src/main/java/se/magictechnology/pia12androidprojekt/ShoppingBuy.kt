package se.magictechnology.pia12androidprojekt

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.consumePurchase
import com.android.billingclient.api.queryProductDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.magictechnology.pia12androidprojekt.models.ShopList

class ShoppingBuy {

    lateinit var activity : Activity

    private val _allproducts = MutableStateFlow<List<ProductDetails>?>(null)
    val allproducts : StateFlow<List<ProductDetails>?> get() = _allproducts

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    handlePurchase(purchase)
                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                // Handle an error caused by a user cancelling the purchase flow.
            } else {
                // Handle any other error codes.
            }
        }


    lateinit var billingClient : BillingClient

    fun setup(act : Activity) {
        activity = act

        billingClient = BillingClient.newBuilder(activity)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    Log.i("pia12debug", "CONNECT OK")
                    // The BillingClient is ready. You can query purchases here.
                    GlobalScope.launch(Dispatchers.IO) {
                        getProducts()
                    }
                } else {
                    Log.i("pia12debug", "CONNECT NOT OK")
                }
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.i("pia12debug", "BILLING DISCONNECT")
            }
        })
    }

    suspend fun getProducts() {
        Log.i("pia12debug", "GET PRODUCTS")

        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("pia12premium")
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )
        val params = QueryProductDetailsParams.newBuilder()
        params.setProductList(productList)

        // leverage queryProductDetails Kotlin extension function
        val productDetailsResult = withContext(Dispatchers.IO) {
            billingClient.queryProductDetails(params.build())
        }

        // Process the result.
        if(productDetailsResult.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            Log.i("pia12debug", "GET PRODUCTS OK")

            _allproducts.value = productDetailsResult.productDetailsList
            // RITA PÅ SKÄRMEN

            for(prod in productDetailsResult.productDetailsList!!) {
                Log.i("pia12debug", prod.productId)
                Log.i("pia12debug", prod.title)
                Log.i("pia12debug", prod.description)
            }
        } else {
            Log.i("pia12debug", "GET PRODUCTS NOT OK")
        }

    }

    fun doBuy(productBuy : ProductDetails) {
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                .setProductDetails(productBuy)
                // For One-time product, "setOfferToken" method shouldn't be called.
                // For subscriptions, to get an offer token, call ProductDetails.subscriptionOfferDetails()
                // for a list of offers that are available to the user
                //.setOfferToken(selectedOfferToken)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        // Launch the billing flow
        val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
    }

    fun handlePurchase(purchase: Purchase) {
        // Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        // Spara på server eller liknande.

        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

        GlobalScope.launch(Dispatchers.IO) {
            billingClient.consumePurchase(consumeParams)
        }

    }


}