package com.app.fcm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.fcm.ads.NativeAds.refreshNativeAd
import com.app.fcm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBindingWithSetContentView(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding
        initializeMobileAdsSdk()
    }

    private fun initializeMobileAdsSdk() {
        CoroutineScope(Dispatchers.IO).launch {
            runOnUiThread {
                // Load an ad on the main thread.
                refreshNativeAd(this@MainActivity)
            }
        }
    }

}