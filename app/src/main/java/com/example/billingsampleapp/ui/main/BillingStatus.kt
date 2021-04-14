package com.example.billingsampleapp.ui.main

import com.android.billingclient.api.BillingResult

sealed class BillingStatus {
    object SetupSuccess: BillingStatus()
    object BillingFlow: BillingStatus()
    object PendingPurchase: BillingStatus()
    object AcknowledgeSuccess: BillingStatus()
    object ServiceDisconnected: BillingStatus()
    object NoPreviousPlanPurchaseHistory: BillingStatus()
    data class Error(val billingResult: BillingResult): BillingStatus()
}