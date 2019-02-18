package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.functional.Either
import com.malmstein.samples.unidirektional.models.Failure
import com.malmstein.samples.unidirektional.models.Success
import com.malmstein.samples.unidirektional.store.MainUseCase
import com.malmstein.samples.unidirektional.store.State
import kotlinx.coroutines.channels.ReceiveChannel

class GetPhotos(private val repository: PhotosRepository): MainUseCase() {

    operator fun invoke() = getPhotos()

    private fun getPhotos(): ReceiveChannel<State<Either<Success, Failure>>> = produceActions {
        send { Either.Left(Success.Loading) }
        try {
            val photos = repository.all().await()
            send { Either.Left(GalleryViewState(photos = photos)) }
        } catch (e: Exception) {
            send { Either.Right(GalleryFailure(e)) }
        }
    }
}
