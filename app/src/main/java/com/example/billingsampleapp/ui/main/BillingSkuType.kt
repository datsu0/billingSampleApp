package com.example.billingsampleapp.ui.main

import com.android.billingclient.api.BillingClient

enum class BillingSkuType (
    @BillingClient.SkuType
    val skuType: String
) {
    Subscription(BillingClient.SkuType.SUBS),
    InAppNonConsumable(BillingClient.SkuType.INAPP),
    InAppConsumable(BillingClient.SkuType.INAPP),
}

enum class BillingItem(var sku: String, val billingSkuType: BillingSkuType) {
    MonthlyPlan(
        "montyly_plan", BillingSkuType.Subscription
    ),
    NonConsumableItem(
        "nonConsumableItem", BillingSkuType.InAppNonConsumable
    ),
    ConsumableItem(
        "consumableItem", BillingSkuType.InAppConsumable
    ),
    AndroidTestPurchased(
        "android.test.purchased", BillingSkuType.InAppNonConsumable
    );

    fun skusList(): List<String> = listOf(sku)
    companion object {
        fun findBySku(sku: String):BillingItem? =
            values().find { it.sku == sku }
    }
}