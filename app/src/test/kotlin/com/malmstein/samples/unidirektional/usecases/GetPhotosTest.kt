package com.malmstein.samples.unidirektional.usecases

import assertk.assertions.*
import com.malmstein.samples.unidirektional.MainUseCaseTest
import com.malmstein.samples.unidirektional.feature.GalleryViewState
import com.malmstein.samples.unidirektional.feature.GetPhotos
import com.malmstein.samples.unidirektional.feature.Photo
import com.malmstein.samples.unidirektional.feature.PhotosRepository
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
        coEvery { repository.all() } returns listOf(PHOTO_1, PHOTO_2)

        val states = runBlocking {
            useCase.invoke().states(GalleryViewState())
        }

        assertk.assert(states).hasSize(2)
        assertk.assert(states[0].loading).isTrue()
        assertk.assert(states[1].error).isNull()
        assertk.assert(states[1].loading).isFalse()
        assertk.assert(states[1].photos).containsExactly(
            PHOTO_1,
            PHOTO_2
        )
    }

    @Test
    fun `changes state on error`() {
        coEvery { repository.all() } returns GlobalScope.async { throw IOException() }

        val states = runBlocking {
            useCase.invoke().states(GalleryViewState())
        }

        assertk.assert(states).hasSize(2)
        assertk.assert(states[0].loading).isTrue()
        assertk.assert(states[1].error).isNotNull()
        assertk.assert(states[1].loading).isFalse()
        assertk.assert(states[1].photos).isEmpty()
    }


}
