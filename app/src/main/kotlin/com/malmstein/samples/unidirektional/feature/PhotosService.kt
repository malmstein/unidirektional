package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.services.IPhotosApi
import com.malmstein.samples.unidirektional.services.PhotoEntity
import retrofit2.Call
import retrofit2.Retrofit

class PhotosService(private val retrofit: Retrofit) : IPhotosApi {

    private val photosApi by lazy { retrofit.create(IPhotosApi::class.java) }

    override fun photos(user: String): Call<List<PhotoEntity>> = photosApi.photos(user)

}
