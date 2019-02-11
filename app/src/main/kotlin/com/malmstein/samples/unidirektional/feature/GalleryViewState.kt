package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.models.Failure
import com.malmstein.samples.unidirektional.models.ViewState

data class GalleryViewState(
        val photos: List<Photo> = emptyList(),
        val loading: Boolean = false,
        val error: Throwable? = null
) : ViewState.Photos()

data class GalleryFailure(val e: Exception): Failure.FeatureFailure()
