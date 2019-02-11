package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.store.MainUseCase
import com.malmstein.samples.unidirektional.store.State
import kotlinx.coroutines.channels.ReceiveChannel

class GetPhotos(private val repository: PhotosRepository): MainUseCase() {

    operator fun invoke() = getOrders()

    private fun getOrders(): ReceiveChannel<State<GalleryViewState>> = produceActions {
        send { copy(loading = true, error = null) }
        try {
            val photos = repository.all().await()
            send { copy(photos = photos, loading = false) }
        } catch (e: Exception) {
            send { copy(error = e, loading = false) }
        }
    }
}
