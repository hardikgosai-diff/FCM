package com.app.fcm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.fcm.databinding.ActivityMainBinding

class HomeFragment : Fragment(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}