package com.wao.tile.ferramentas


import android.content.Context
import com.android.billingclient.api.*

class BillingManager(
    private val context: Context,
    private val onCoinsPurchased: (Int) -> Unit,
    private val onRemoveAdsPurchased: () -> Unit
) {
    private val billingClient: BillingClient = BillingClient.newBuilder(context)
        .enablePendingPurchases()
        .setListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) handlePurchase(purchase)
            }
        }.build()

    init {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                // Opcional: verificar compras pendentes ou não reconhecidas
                billingClient.queryPurchasesAsync(
                    QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()
                ) { _, purchases ->
                    purchases?.forEach { handlePurchase(it) }
                }
            }

            override fun onBillingServiceDisconnected() {
                // Tentar reconectar se necessário
            }
        })
    }

    fun launchPurchaseFlow(activity: android.app.Activity, productId: String) {
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
                val productDetails = productDetailsList[0]

                val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .build()

                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(listOf(productDetailsParams))
                    .build()

                billingClient.launchBillingFlow(activity, billingFlowParams)
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            when (purchase.products[0]) {

                // 🔁 Produto consumível: pode comprar várias vezes
                "coins_1000" -> {
                    val consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()

                    billingClient.consumeAsync(consumeParams) { billingResult, _ ->
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            onCoinsPurchased(1000)
                        }
                    }
                }

                // ✅ Produto não consumível: só compra uma vez
                "remove_ads" -> {
                    if (!purchase.isAcknowledged) {
                        val acknowledgeParams = AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.purchaseToken)
                            .build()

                        billingClient.acknowledgePurchase(acknowledgeParams) { billingResult ->
                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                salvarCompra("remove_ads")
                                onRemoveAdsPurchased()
                            }
                        }
                    } else {
                        // Caso já esteja reconhecido
                        onRemoveAdsPurchased()
                    }
                }
            }
        }
    }

    private fun salvarCompra(produto: String) {
        val prefs = context.getSharedPreferences("jogo", Context.MODE_PRIVATE)
        prefs.edit().putBoolean(produto, true).apply()
    }

    fun foiComprado(produto: String): Boolean {
        val prefs = context.getSharedPreferences("jogo", Context.MODE_PRIVATE)
        return prefs.getBoolean(produto, false)
    }
}
