package com.malmstein.samples.unidirektional.infrastructure.network

import android.content.Context
import com.malmstein.samples.unidirektional.extensions.networkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}
