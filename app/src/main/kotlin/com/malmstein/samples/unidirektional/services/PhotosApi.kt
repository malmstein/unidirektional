package com.malmstein.samples.unidirektional.services

import com.google.gson.annotations.SerializedName
import com.malmstein.samples.unidirektional.feature.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class PhotoEntity(@SerializedName("imageUrl") val imageUrl: String){
    fun toPhoto(): Photo {
        return Photo(imageUrl)
    }
}

interface IPhotosApi {

    @GET("/v4/user/{user}/photos")
    fun photos(@Path("user") user: String): Call<List<PhotoEntity>>

}
