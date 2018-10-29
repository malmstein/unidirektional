package com.malmstein.samples.unidirektional.infrastructure.injection

import com.google.gson.Gson
import com.malmstein.samples.unidirektional.BuildConfig
import com.malmstein.samples.unidirektional.feature.*
import com.malmstein.samples.unidirektional.infrastructure.network.NetworkHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Properties {
    const val BASE_URL = "https://api.whatever/"
}

val networkModule = module {

    single { NetworkHandler(androidContext()) }
    single { createOkHttpClient() }
    single { createRetrofit(get()) }

}

val dataModule = module {

    single { PhotosService(get()) }
    single<PhotosRepository> { PhotosRepositoryImpl(get(), get()) }

}

val useCaseModule = module {

    single { GetPhotos(get()) }

}

val viewModelModule = module {

    viewModel { GalleryViewModel(get()) }

}

fun createRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Properties.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(httpClient)
        .build()
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    return clientBuilder.build()
}
