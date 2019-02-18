package com.malmstein.samples.unidirektional.usecases

import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.malmstein.samples.unidirektional.MainUseCaseTest
import com.malmstein.samples.unidirektional.feature.*
import com.malmstein.samples.unidirektional.functional.Either
import com.malmstein.samples.unidirektional.models.Success
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

val PHOTO_1 = Photo("imageUrl")
val PHOTO_2 = Photo("imageUrl")

class GetPhotosTest : MainUseCaseTest() {

    private val repository: PhotosRepository = mockk()

    private val useCase = GetPhotos(repository)

    @Test
    fun `changes state on successful loading`() {
        val expectedState = GalleryViewState(listOf(PHOTO_1, PHOTO_2))

        coEvery { repository.all() } returns listOf(PHOTO_1, PHOTO_2)

        val states = runBlocking {
            useCase.invoke().states(Either.Left(Success.Idle))
        }

        assertk.assert(states).hasSize(2)
        assertk.assert(states[0]).isEqualTo(Either.Left(Success.Loading))
        assertk.assert(states[1]).isEqualTo(Either.Left(expectedState))
    }

    @Test
    fun `changes state on error`() {
        val expectedException = IOException()
        coEvery { repository.all() } returns GlobalScope.async { throw expectedException }

        val states = runBlocking {
            useCase.invoke().states(Either.Left(Success.Idle))
        }

        assertk.assert(states).hasSize(2)
        assertk.assert(states[0]).isEqualTo(Either.Left(Success.Loading))
        assertk.assert(states[1]).isEqualTo(Either.Right(GalleryFailure(expectedException)))
    }


}
