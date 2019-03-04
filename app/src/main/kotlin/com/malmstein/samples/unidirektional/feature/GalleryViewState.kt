package com.malmstein.samples.unidirektional.feature

import com.malmstein.samples.unidirektional.models.Failure
import com.malmstein.samples.unidirektional.models.Success

data class GalleryViewState(val photos: List<Photo> = emptyList()) : Success.ViewState()
data class GalleryFailure(val e: Exception) : Failure.FeatureFailure()
