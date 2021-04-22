package com.example.billingsampleapp.ui.main

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode.OK

class BillingViewModel(activity: Activity) : ViewModel(),
    BillingClientStateListener, PurchasesUpdatedListener, LifecycleObserver {

    private val purchasesUpdatedListener = PurchasesUpdatedListener {
        billingResult, list ->
    }

    private val billingClient: BillingClient =
        BillingClient.newBuilder(activity)
            .enablePendingPurchases()
            .setListener(purchasesUpdatedListener)
            .build()


    private val _billingStatus = MutableLiveData<BillingStatus>()
    val billingStatus: LiveData<BillingStatus> = _billingStatus



    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if(billingResult.responseCode == OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        }else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED){

        }else {

        }
    }

    fun handlePurchase(purchases: Purchase){

    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK)
            _billingStatus.postValue(BillingStatus.SetupSuccess)
        else
            _billingStatus.postValue(BillingStatus.Error(billingResult))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == OK) {
                    TODO("Not yet implemented")
                }
            }

            override fun onBillingServiceDisconnected() {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        billingClient.endConnection()
    }

    override fun onBillingServiceDisconnected() = Unit

    private fun getSkuDetails(
        billingItem: BillingItem,
        onResponse: (skuDetails: SkuDetails) -> Unit
    ) {
        val skuDetailsParams = SkuDetailsParams.newBuilder()
            .setSkusList(billingItem.skusList())
            .setType(billingItem.billingSkuType.skuType)
            .build()

        billingClient.querySkuDetailsAsync(skuDetailsParams) {
                billingResult: BillingResult, skuDetailList: List<SkuDetails> ->
            if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.OK && skuDetailList.isNotEmpty()
            ) {
                onResponse(skuDetailList[0])
            } else {
                _billingStatus.postValue(BillingStatus.Error(billingResult))
            }

        }
    }

    fun purchase(activity: Activity, billingItem: BillingItem) {
        getSkuDetails(billingItem){skuDetails ->
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build()
            _billingStatus.postValue(BillingStatus.BillingFlow)
            billingClient.launchBillingFlow(activity, flowParams)
        }
    }

}

private fun BillingClient.querySkuDetailsAsync(skuDetailsParams: SkuDetailsParams?, function: (BillingResult, List<SkuDetails>) -> Unit) {

}

