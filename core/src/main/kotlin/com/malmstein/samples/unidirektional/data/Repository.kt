package com.malmstein.samples.unidirektional.data

import retrofit2.Call

interface Repository {
    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): R {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> transform((response.body() ?: default))
                false -> transform(default)
            }
        } catch (exception: Throwable) {
            transform(default)
        }
    }
}
