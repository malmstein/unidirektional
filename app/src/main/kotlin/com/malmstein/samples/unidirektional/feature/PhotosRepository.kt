package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.data.Repository
import com.malmstein.samples.unidirektional.infrastructure.network.NetworkHandler
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.coroutineScope

data class Photo(val imageUrl: String)

interface PhotosRepository : Repository {
    suspend fun all(): Deferred<List<Photo>>
}

class PhotosRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val api: PhotosService
) : PhotosRepository {
    override suspend fun all(): Deferred<List<Photo>> = coroutineScope {
        async {
            when (networkHandler.isConnected) {
                true -> loadPhotos()
                false, null -> loadFallback()
            }
        }
    }

    private fun loadPhotos(): List<Photo> {
        return request(api.photos("user"), { it.map { it.toPhoto() } }, emptyList())
    }

    private fun loadFallback(): List<Photo> {
        return emptyList()
    }

}

