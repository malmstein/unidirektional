package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.functional.Either
import com.malmstein.samples.unidirektional.models.Failure
import com.malmstein.samples.unidirektional.models.ViewState
import com.malmstein.samples.unidirektional.store.MainUseCase
import com.malmstein.samples.unidirektional.store.State
import com.malmstein.samples.unidirektional.store.ViewStateStore
import kotlinx.coroutines.channels.ReceiveChannel

class GetPhoto(private val repository: PhotosRepository): MainUseCase() {

    operator fun invoke() = getOrders()

    private fun getOrders(): ReceiveChannel<State<Either<ViewState, Failure>>> = produceActions {
        send { Either.Left(GalleryViewState(loading = true, error = null)) }
        try {
            val photos = repository.all().await()
            send { Either.Left(GalleryViewState(photos = photos, loading = false)) }
        } catch (e: Exception) {
            send { Either.Right(GalleryFailure(e)) }
        }
    }
}
