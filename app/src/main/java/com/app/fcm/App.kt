package com.app.fcm

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.HiltAndroidApp

/**
 * 05/06/25.
 *
 * @author hardkgosai.
 */

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val requestConfiguration = RequestConfiguration.Builder()
            .setTestDeviceIds(listOf("D587570D00865B0C6250B1EF9CBA9723")).build()
        MobileAds.setRequestConfiguration(requestConfiguration)
        MobileAds.initialize(this)
    }

}