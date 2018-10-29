package com.malmstein.samples.unidirektional

import android.app.Application
import com.malmstein.samples.unidirektional.infrastructure.injection.dataModule
import com.malmstein.samples.unidirektional.infrastructure.injection.networkModule
import com.malmstein.samples.unidirektional.infrastructure.injection.useCaseModule
import com.malmstein.samples.unidirektional.infrastructure.injection.viewModelModule
import org.koin.android.ext.android.startKoin

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            this, listOf(
                dataModule,
                useCaseModule,
                networkModule,
                viewModelModule
            )
        )
    }
}
